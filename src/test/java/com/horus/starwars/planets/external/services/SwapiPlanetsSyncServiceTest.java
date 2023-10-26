package com.horus.starwars.planets.external.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.external.swapi.repositories.SwapiSyncResilienceRepository;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiFindManyPlantesDTO;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiPlanetDTO;
import com.horus.starwars.planets.external.swapi.services.SwapiPlanetsSyncService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.horus.starwars.planets.external.swapi.utils.SwapiUrlHelpers.PLANETS_SERVICE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwapiPlanetsSyncServiceTest {
    @Mock
    private PlanetsRepository planetsRepository;

    @Mock
    private SwapiSyncResilienceRepository swapiSyncResilienceRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiPlanetsSyncService swapiPlanetsSyncService;

    @BeforeEach
    public void init() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://swapi.dev/api"));
    }

    @Test
    public void shouldReturnTrueOnSyncronized() {
        Planet expected = new Planet();
        expected.setId(UUID.randomUUID());
        expected.setName("New planet");
        expected.setClimate("mock");
        expected.setTerrain("mock");
        expected.setCreatedAt(LocalDateTime.now());
        expected.setDeleted(false);


        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT, SwapiFindManyPlantesDTO.class)).thenReturn(new SwapiFindManyPlantesDTO(1));
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT.concat("/1"), SwapiPlanetDTO.class)).thenReturn(new SwapiPlanetDTO(1, "New planet", "mock", "mock"));
        when(planetsRepository.findByName(any())).thenReturn(Optional.empty());

        swapiPlanetsSyncService.execute();

        verify(planetsRepository, times(1)).save(any());
    }

    @Test
    public void shouldStopOnNoPlanetsFound() {
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT, SwapiFindManyPlantesDTO.class)).thenReturn(null);

        swapiPlanetsSyncService.execute();
    }

    @Test
    public void shouldThrowAnRestClientException() {
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT, SwapiFindManyPlantesDTO.class)).thenReturn(new SwapiFindManyPlantesDTO(1));
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT.concat("/1"), SwapiPlanetDTO.class)).thenThrow(new RestClientException("Stubbing Exception"));

        swapiPlanetsSyncService.execute();

        verify(swapiSyncResilienceRepository, times(1)).save(any());
    }

    @Test
    public void shouldThrowAnException() {
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT, SwapiFindManyPlantesDTO.class)).thenReturn(new SwapiFindManyPlantesDTO(1));
        when(restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT.concat("/1"), SwapiPlanetDTO.class)).thenReturn(new SwapiPlanetDTO(1, "New planet", "mock", "mock"));
        when(planetsRepository.findByName(any())).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(Exception.class, () -> swapiPlanetsSyncService.execute());
    }
}
