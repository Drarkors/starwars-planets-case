package com.horus.starwars.planets.external.swapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class SwapiRestConfiguration {

    private final String swapiUrl;

    public SwapiRestConfiguration(@Value("${swapi.url}") String swapiUrl) {
        this.swapiUrl = swapiUrl;
    }

    @Bean(name = "SwapiRest")
    public RestTemplate swapiRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(swapiUrl));

        return restTemplate;
    }
}
