package com.horus.starwars.planets.domain.planets.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatePlanetRequestDTO {
    private String name;
    private String climate;
    private String terrain;
}
