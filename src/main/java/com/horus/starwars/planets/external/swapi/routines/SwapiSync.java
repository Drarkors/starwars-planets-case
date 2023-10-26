package com.horus.starwars.planets.external.swapi.routines;

import com.horus.starwars.planets.external.swapi.services.SwapiFilmsSyncCheckService;
import com.horus.starwars.planets.external.swapi.services.SwapiPlanetsSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SwapiSync {
    private final SwapiFilmsSyncCheckService swapiFilmsSyncCheckService;

    private final SwapiPlanetsSyncService swapiPlanetsSyncService;

    @Autowired
    public SwapiSync(SwapiFilmsSyncCheckService swapiFilmsSyncCheckService, SwapiPlanetsSyncService swapiPlanetsSyncService) {
        this.swapiFilmsSyncCheckService = swapiFilmsSyncCheckService;
        this.swapiPlanetsSyncService = swapiPlanetsSyncService;
    }
    
    @Scheduled(cron = "${app.routines.planet-sync.cron}")
//    @Scheduled(fixedDelay = 1000)
    public void scheduledPlanetSync() {
        var isSynced = swapiFilmsSyncCheckService.execute();

        if (!isSynced) {
            swapiPlanetsSyncService.execute();
        }
    }
}
