package com.horus.starwars.planets.external.swapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "swapi_sync_resilience")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SwapiSyncResilience {
    @Id
    @Column(name = "swapi_id")
    private Integer swapiId;

    @Column(name = "total_tries")
    private Integer totalTries;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void syncTry() {
        this.totalTries = this.totalTries != null ? totalTries++ : 1;
        this.updatedAt = LocalDateTime.now();

        if (totalTries % 3 == 0) {
            this.isDeleted = true;
            this.deletedAt = LocalDateTime.now();
        }
    }

}
