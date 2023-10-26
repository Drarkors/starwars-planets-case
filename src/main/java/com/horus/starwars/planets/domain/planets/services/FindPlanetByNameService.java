package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindPlanetByNameService {

    private final PlanetsRepository planetsRepository;

    @Autowired
    public FindPlanetByNameService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public Planet execute(String name) {
        var planet = planetsRepository.findByName(name);

        if (planet.isEmpty() || planet.get().isDeleted()) {
            throw new NotFoundException("No planet found with provided name");
        }

        return planet.get();
    }
}
