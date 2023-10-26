package com.horus.starwars.planets.external.swapi.repositories;

import com.horus.starwars.planets.external.swapi.entities.SwapiSyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwapiSyncInfoRepository extends JpaRepository<SwapiSyncInfo, String> {
}
