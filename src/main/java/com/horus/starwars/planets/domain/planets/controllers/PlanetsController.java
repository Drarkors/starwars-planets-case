package com.horus.starwars.planets.domain.planets.controllers;

import com.horus.starwars.planets.domain.planets.dtos.CreatePlanetRequestDTO;
import com.horus.starwars.planets.domain.planets.dtos.FindManyPlanetsRequestDTO;
import com.horus.starwars.planets.domain.planets.mappers.PlanetMapper;
import com.horus.starwars.planets.domain.planets.services.*;
import com.horus.starwars.planets.domain.planets.validators.CreatePlanetValidator;
import com.horus.starwars.planets.domain.shared.validators.PageValidator;
import com.horus.starwars.planets.domain.shared.validators.ParamIdValidator;
import com.horus.starwars.planets.domain.shared.validators.ParamNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planets")
public class PlanetsController {

    private final PageValidator pageValidator;
    private final ParamIdValidator paramIdValidator;
    private final ParamNameValidator paramNameValidator;
    private final CreatePlanetValidator createPlanetValidator;
    private final FindManyPlanetsService findManyPlanetsService;
    private final FindPlanetByIdService findPlanetByIdService;
    private final FindPlanetByNameService findPlanetByNameService;
    private final CreatePlanetService createPlanetService;
    private final DeletePlanetService deletePlanetService;

    @Autowired
    public PlanetsController(
            PageValidator pageValidator,
            ParamIdValidator paramIdValidator,
            CreatePlanetValidator createPlanetValidator,
            ParamNameValidator paramNameValidator,
            FindManyPlanetsService findManyPlanetsService,
            FindPlanetByIdService findPlanetByIdService,
            FindPlanetByNameService findPlanetByNameService,
            CreatePlanetService createPlanetService,
            DeletePlanetService deletePlanetService
    ) {
        this.pageValidator = pageValidator;
        this.paramIdValidator = paramIdValidator;
        this.paramNameValidator = paramNameValidator;
        this.createPlanetValidator = createPlanetValidator;
        this.findManyPlanetsService = findManyPlanetsService;
        this.findPlanetByIdService = findPlanetByIdService;
        this.findPlanetByNameService = findPlanetByNameService;
        this.createPlanetService = createPlanetService;
        this.deletePlanetService = deletePlanetService;
    }

    @GetMapping()
    public ResponseEntity<?> findManyPlanets(@RequestBody FindManyPlanetsRequestDTO findManyPlanetsRequest) {
        pageValidator.validate(findManyPlanetsRequest.getPage(), findManyPlanetsRequest.getPageSize());

        var planets = findManyPlanetsService.execute(findManyPlanetsRequest.getPage(), findManyPlanetsRequest.getPageSize());

        var response = PlanetMapper.toListResponseDTO(planets);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") String id) {
        paramIdValidator.validate(id);

        var planet = findPlanetByIdService.execute(id);

        return ResponseEntity.ok(PlanetMapper.toResponseDTO(planet));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> findByName(@PathVariable(name = "name") String name) {
        paramNameValidator.validate(name);

        return ResponseEntity.ok(findPlanetByNameService.execute(name));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreatePlanetRequestDTO createPlanetRequest) {
        createPlanetValidator.validate(createPlanetRequest);

        var planet = createPlanetService.execute(createPlanetRequest);

        return ResponseEntity.ok(PlanetMapper.toResponseDTO(planet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        paramIdValidator.validate(id);

        deletePlanetService.execute(id);

        return ResponseEntity.noContent().build();
    }
}
