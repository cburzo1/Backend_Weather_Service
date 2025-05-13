package com.chrisweatherproject.weatherproject.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //Enables All external APIs like Open Weather

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}