package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.dtos.CreatePlanetRequestDTO;
import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatePlanetServiceTest {
    @Mock
    private PlanetsRepository planetsRepository;

    @InjectMocks
    private CreatePlanetService createPlanetService;

    @Test
    public void shouldCreateAPlanet() {
        Planet expected = new Planet();
        expected.setId(UUID.randomUUID());
        expected.setName("New planet");
        expected.setClimate("mock");
        expected.setTerrain("mock");
        expected.setCreatedAt(LocalDateTime.now());

        when(planetsRepository.save(any())).thenReturn(expected);

        var actual = createPlanetService.execute(new CreatePlanetRequestDTO("New Planet", "mock", "mock"));

        assertEquals(expected, actual);
    }
}
