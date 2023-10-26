package com.horus.starwars.planets.external.swapi.routines.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SwapiPlanetDTO {
    private Integer id;
    private String name;
    private String climate;
    private String terrain;
}
