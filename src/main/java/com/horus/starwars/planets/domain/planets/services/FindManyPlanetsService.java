package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindManyPlanetsService {

    private final PlanetsRepository planetsRepository;

    @Autowired
    public FindManyPlanetsService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public Page<Planet> execute(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);

        var rawPlanets = planetsRepository.findAll(pageRequest);

        return planetsRepository.findAll(pageRequest);
    }
}
