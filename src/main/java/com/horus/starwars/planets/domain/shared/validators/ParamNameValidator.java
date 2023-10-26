package com.horus.starwars.planets.domain.shared.validators;

import com.horus.starwars.planets.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ParamNameValidator {
    public void validate(String name) {
        if (name == null || name.isEmpty())
            throw new NotFoundException("An name must be provided!");
    }
}
