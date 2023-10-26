package com.horus.starwars.planets.external.services;

import com.horus.starwars.planets.external.swapi.entities.SwapiSyncInfo;
import com.horus.starwars.planets.external.swapi.repositories.SwapiSyncInfoRepository;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiFindManyFilmsDTO;
import com.horus.starwars.planets.external.swapi.services.SwapiFilmsSyncCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"swapi.url=https://swapi.dev/api"})
public class SwapiFilmsSyncCheckServiceTest {

    @Mock
    private SwapiSyncInfoRepository swapiSyncInfoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiFilmsSyncCheckService swapiFilmsSyncCheckService;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(swapiFilmsSyncCheckService, "swapiUrl", "https://swapi.dev/api");
    }

    @Test
    public void shouldReturnTrueOnSyncronized() {
        when(restTemplate.getForObject("https://swapi.dev/api/films", SwapiFindManyFilmsDTO.class)).thenReturn(new SwapiFindManyFilmsDTO(6));
        when(swapiSyncInfoRepository.findById("films")).thenReturn(Optional.of(new SwapiSyncInfo("films", 6, LocalDateTime.now())));

        var isSync = swapiFilmsSyncCheckService.execute();

        assertThat(isSync).isTrue();
    }

    @Test
    public void shouldReturnFalseOnMissingFilms() {
        when(restTemplate.getForObject("https://swapi.dev/api/films", SwapiFindManyFilmsDTO.class)).thenReturn(new SwapiFindManyFilmsDTO(6));
        when(swapiSyncInfoRepository.findById("films")).thenReturn(Optional.of(new SwapiSyncInfo("films", 2, LocalDateTime.now())));

        var isSync = swapiFilmsSyncCheckService.execute();

        assertThat(isSync).isFalse();
    }
}
