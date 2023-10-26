package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DeletePlanetService {
    private final PlanetsRepository planetsRepository;

    @Autowired
    public DeletePlanetService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public void execute(String id) {
        var planetOpt = planetsRepository.findById(UUID.fromString(id));

        if (planetOpt.isEmpty() || planetOpt.get().isDeleted()) {
            throw new NotFoundException("No planet found with provided id");
        }

        var planet = planetOpt.get();
        planet.setDeleted(true);
        planet.setDeletedAt(LocalDateTime.now());

        planetsRepository.save(planet);
    }
}
