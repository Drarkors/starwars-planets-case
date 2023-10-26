package com.horus.starwars.planets.external.swapi.services;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import com.horus.starwars.planets.domain.planets.repositories.PlanetsRepository;
import com.horus.starwars.planets.external.swapi.entities.SwapiSyncResilience;
import com.horus.starwars.planets.external.swapi.repositories.SwapiSyncResilienceRepository;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiFindManyPlantesDTO;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiPlanetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static com.horus.starwars.planets.external.swapi.utils.SwapiUrlHelpers.PLANETS_SERVICE_ENDPOINT;

@Service
public class SwapiPlanetsSyncService {

    private static final Logger log = LoggerFactory.getLogger(SwapiPlanetsSyncService.class);

    private final PlanetsRepository planetsRepository;

    private final SwapiSyncResilienceRepository swapiSyncResilienceRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public SwapiPlanetsSyncService(PlanetsRepository planetsRepository, SwapiSyncResilienceRepository swapiSyncResilienceRepository, @Qualifier("SwapiRest") RestTemplate restTemplate) {
        this.planetsRepository = planetsRepository;
        this.swapiSyncResilienceRepository = swapiSyncResilienceRepository;
        this.restTemplate = restTemplate;
    }

    public void execute() {
        log.info("Sync-ing Planets...");

        var swapiPlanets = restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT, SwapiFindManyPlantesDTO.class);

        if (swapiPlanets == null) {
            log.warn("[SWAPI BAD FORMAT] Could not find planets on " + PLANETS_SERVICE_ENDPOINT);
            return;
        }

        IntStream.range(1, swapiPlanets.getCount()).parallel().forEach(this::syncPlanet);
    }

    private void syncPlanet(int i) {
        try {
            var swapiPlanet = restTemplate.getForObject(PLANETS_SERVICE_ENDPOINT.concat("/").concat(String.valueOf(i)), SwapiPlanetDTO.class);

            if (swapiPlanet != null && !swapiPlanet.getName().isEmpty()) {
                var planet = planetsRepository.findByName(swapiPlanet.getName()).orElse(new Planet(swapiPlanet.getName(), LocalDateTime.now()));
                planet.setClimate(swapiPlanet.getClimate());
                planet.setTerrain(swapiPlanet.getTerrain());
                planet.setSwapiId(swapiPlanet.getId());
                planet.setUpdatedAt(LocalDateTime.now());

                planetsRepository.save(planet);
            }
        } catch (RestClientException restClientException) {
            log.warn("[SWAPI INCONSISTENCY] Could not find planet with ID: " + i);
            log.warn("[SWAPI INCONSISTENCY] Exception: " + restClientException.getMessage());

            var inconsistentPlanet = swapiSyncResilienceRepository.findById(i).orElse(new SwapiSyncResilience());

            if (inconsistentPlanet.getSwapiId() == null) {
                inconsistentPlanet.setSwapiId(i);
                inconsistentPlanet.setCreatedAt(LocalDateTime.now());
            }

            inconsistentPlanet.syncTry();

            swapiSyncResilienceRepository.save(inconsistentPlanet);
        } catch (Exception exception) {
            log.error("[Unexpected Error] Unexpected error: " + exception.getMessage());

            throw exception;
        }
    }
}
