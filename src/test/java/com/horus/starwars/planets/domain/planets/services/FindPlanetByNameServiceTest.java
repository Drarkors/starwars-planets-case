package com.horus.starwars.planets.domain.planets.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindPlanetByNameServiceTest {

    @Mock
    private PlanetsRepository planetsRepository;

    @InjectMocks
    private FindPlanetByNameService findPlanetByNameService;

    @Test
    public void shouldFindAPlanet() {
        Planet expected = new Planet();
        expected.setId(UUID.randomUUID());
        expected.setName("New planet");
        expected.setClimate("mock");
        expected.setTerrain("mock");
        expected.setCreatedAt(LocalDateTime.now());
        expected.setDeleted(false);

        when(planetsRepository.findByName(expected.getName())).thenReturn(Optional.of(expected));

        var actual = findPlanetByNameService.execute(expected.getName());

        assertEquals(actual, expected);
    }

    @Test
    public void shouldThrowANotFoundException() {
        when(planetsRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findPlanetByNameService.execute(UUID.randomUUID().toString()));
    }
}
