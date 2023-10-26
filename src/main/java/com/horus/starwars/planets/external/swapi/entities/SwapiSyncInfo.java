package com.horus.starwars.planets.external.swapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "swapi_sync_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SwapiSyncInfo {
    @Id
    @Column(nullable = false)
    private String entity;

    @Column(name = "total_records", nullable = false)
    private Integer totalRecords;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
