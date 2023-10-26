package com.horus.starwars.planets.domain.planets.repositories;

import com.horus.starwars.planets.domain.planets.entities.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanetsRepository extends JpaRepository<Planet, UUID> {
    Optional<Planet> findByName(String name);

    @Override
    @Query(
            value = "SELECT * FROM planets WHERE is_deleted = FALSE",
            countQuery = "SELECT COUNT(*) FROM planets WHERE is_deleted = FALSE",
            nativeQuery = true
    )
    Page<Planet> findAll(Pageable pageable);
}
