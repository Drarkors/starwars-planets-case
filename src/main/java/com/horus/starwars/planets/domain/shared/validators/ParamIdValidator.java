package com.horus.starwars.planets.domain.shared.validators;

import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ParamIdValidator {

    public void validate(String id) {
        if (id == null || id.equals("0") || id.isEmpty())
            throw new NotFoundException("An ID must be provided!");
    }
}
