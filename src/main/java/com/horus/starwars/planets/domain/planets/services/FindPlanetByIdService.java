package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindPlanetByIdService {

    private final PlanetsRepository planetsRepository;

    @Autowired
    public FindPlanetByIdService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public Planet execute(String id) {
        UUID uuid = UUID.fromString(id);

        var planet = planetsRepository.findById(uuid);

        if (planet.isEmpty() || planet.get().isDeleted()) {
            throw new NotFoundException("No planet found with provided id");
        }

        return planet.get();
    }
}
