package com.horus.starwars.planets.domain.planets.mappers;

import com.horus.starwars.planets.domain.planets.dtos.ManyPlanetsResponseDTO;
import com.horus.starwars.planets.domain.planets.dtos.PlanetResponseDTO;
import com.horus.starwars.planets.domain.planets.entities.Planet;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PlanetMapper {
    public static PlanetResponseDTO toResponseDTO(Planet planet) {
        PlanetResponseDTO response = new PlanetResponseDTO();

        response.setId(planet.getId().toString());
        response.setName(planet.getName());
        response.setClimate(planet.getClimate());
        response.setTerrain(planet.getTerrain());
        response.setCreatedAt(planet.getCreatedAt().toString());
        response.setUpdatedAt(planet.getUpdatedAt() != null ? planet.getUpdatedAt().toString() : null);

        return response;
    }

    public static ManyPlanetsResponseDTO toListResponseDTO(Page<Planet> page) {
        var list = new ManyPlanetsResponseDTO();

        list.setContent(page.getContent().stream().map(PlanetMapper::toResponseDTO).collect(Collectors.toList()));
        list.setPage(page.getNumber());
        list.setPageSize(page.getSize());
        list.setTotalPages(page.getTotalPages());
        list.setTotalElements(page.getTotalElements());

        return list;
    }
}
