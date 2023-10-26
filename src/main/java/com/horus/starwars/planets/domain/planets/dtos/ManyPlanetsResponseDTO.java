package com.horus.starwars.planets.domain.planets.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ManyPlanetsResponseDTO implements Serializable {
    private List<PlanetResponseDTO> content;
    private int page;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
}
