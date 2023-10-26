package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindManyPlanetsServiceTest {

    @Mock
    private PlanetsRepository planetsRepository;

    @InjectMocks
    private FindManyPlanetsService findManyPlanetsService;

    @Test
    public void shouldReturnAListOfPlanets() {
        Planet expected = new Planet();
        expected.setId(UUID.randomUUID());
        expected.setName("New planet");
        expected.setClimate("mock");
        expected.setTerrain("mock");
        expected.setCreatedAt(LocalDateTime.now());
        expected.setDeleted(false);

        List<Planet> planets = new ArrayList<>();
        planets.add(expected);

        PageImpl<Planet> page = new PageImpl<>(planets);

        when(planetsRepository.findAll((Pageable) any())).thenReturn(page);

        var actual = findManyPlanetsService.execute(1, 1);

        assertEquals(actual, page);
    }
}
