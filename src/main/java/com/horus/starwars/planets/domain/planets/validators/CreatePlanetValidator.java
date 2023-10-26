package com.horus.starwars.planets.domain.planets.validators;

import com.horus.starwars.planets.domain.planets.dtos.CreatePlanetRequestDTO;
import com.horus.starwars.planets.domain.shared.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CreatePlanetValidator {
    public void validate(CreatePlanetRequestDTO createPlanetRequestDTO) {
        if (createPlanetRequestDTO == null) {
            throw new BadRequestException("No data has been provided");
        }

        if (createPlanetRequestDTO.getName() == null || createPlanetRequestDTO.getName().isEmpty()) {
            throw new BadRequestException("Missing name property");
        }

        if (createPlanetRequestDTO.getClimate() == null || createPlanetRequestDTO.getClimate().isEmpty()) {
            throw new BadRequestException("Missing climate property");
        }

        if (createPlanetRequestDTO.getTerrain() == null || createPlanetRequestDTO.getTerrain().isEmpty()) {
            throw new BadRequestException("Missing terrain property");
        }
    }
}
