package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.dtos.CreatePlanetRequestDTO;
import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.domain.shared.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreatePlanetService {

    private final PlanetsRepository planetsRepository;

    @Autowired
    public CreatePlanetService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public Planet execute(CreatePlanetRequestDTO planetDto) {
        if (planetsRepository.findByName(planetDto.getName()).isPresent()) {
            throw new UnprocessableEntityException("A " + planetDto.getName() + " already exists!");
        }

        Planet planet = new Planet();
        planet.setName(planetDto.getName());
        planet.setClimate(planetDto.getClimate());
        planet.setTerrain(planetDto.getTerrain());
        planet.setCreatedAt(LocalDateTime.now());

        return planetsRepository.save(planet);
    }
}
