package com.horus.starwars.planets.external.swapi.services;

import com.horus.starwars.planets.external.swapi.entities.SwapiSyncInfo;
import com.horus.starwars.planets.external.swapi.repositories.SwapiSyncInfoRepository;
import com.horus.starwars.planets.external.swapi.routines.dtos.SwapiFindManyFilmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.horus.starwars.planets.external.swapi.utils.SwapiUrlHelpers.FILMS_SERVICE_ENDPOINT;

@Service
public class SwapiFilmsSyncCheckService {

    private static final Logger log = LoggerFactory.getLogger(SwapiFilmsSyncCheckService.class);

    private final SwapiSyncInfoRepository swapiSyncInfoRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public SwapiFilmsSyncCheckService(SwapiSyncInfoRepository swapiSyncInfoRepository, @Qualifier("SwapiRest") RestTemplate restTemplate) {
        this.swapiSyncInfoRepository = swapiSyncInfoRepository;
        this.restTemplate = restTemplate;
    }

    public boolean execute() {
        log.info("Checking films...");

        var swapiFilms = restTemplate.getForObject(FILMS_SERVICE_ENDPOINT, SwapiFindManyFilmsDTO.class);

        if (swapiFilms == null)
            return true;

        try {
            var opt = swapiSyncInfoRepository.findById("films");

            if (opt.isEmpty() || opt.get().getTotalRecords() != swapiFilms.getCount()) {
                saveSwapiSyncInfo(swapiFilms.getCount());

                return false;
            }
        } catch (NullPointerException nullPointerException) {
            saveSwapiSyncInfo(swapiFilms.getCount());

            return false;
        }

        return true;
    }

    private void saveSwapiSyncInfo(int count) {
        var swapiFilmsInfo = new SwapiSyncInfo();
        swapiFilmsInfo.setEntity("films");
        swapiFilmsInfo.setTotalRecords(count);
        swapiFilmsInfo.setUpdatedAt(LocalDateTime.now());

        swapiSyncInfoRepository.save(swapiFilmsInfo);
    }
}
