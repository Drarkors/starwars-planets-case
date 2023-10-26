package com.horus.starwars.planets.domain.planets.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindManyPlanetsRequestDTO {

    @JsonProperty(value = "page", required = true)
    private Integer page;

    @JsonProperty(value = "page_size", required = true)
    private Integer pageSize;
}
