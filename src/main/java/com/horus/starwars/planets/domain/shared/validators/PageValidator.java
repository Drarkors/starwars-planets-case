package com.horus.starwars.planets.domain.shared.validators;

import com.horus.starwars.planets.domain.shared.exceptions.BadRequestException;
import com.horus.starwars.planets.domain.shared.exceptions.UnprocessableEntityException;
import org.springframework.stereotype.Component;

@Component
public class PageValidator {

    public void validate(Integer page, Integer pageSize) {
        isPageValid(page);
        isPageSizeValid(pageSize);
    }

    private void isPageValid(Integer page) {
        if (page == null || page == 0)
            throw new BadRequestException("A page must be provided");
    }

    private void isPageSizeValid(Integer pageSize) {
        if (pageSize == null || pageSize == 0)
            throw new BadRequestException("A page must be provided");

        if (pageSize < 5)
            throw new UnprocessableEntityException("A page must have at least 5 items");
    }
}
