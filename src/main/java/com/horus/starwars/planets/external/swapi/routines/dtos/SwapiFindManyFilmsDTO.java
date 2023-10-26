package com.horus.starwars.planets.external.swapi.routines.dtos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SwapiFindManyFilmsDTO implements Serializable {
    private int count;
}
