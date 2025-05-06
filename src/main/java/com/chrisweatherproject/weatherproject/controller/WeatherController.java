package com.chrisweatherproject.weatherproject.controller;

import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/add")
    public String addWeather(@RequestBody Weather weather){
        weatherService.addWeather(weather);

        System.out.println(weather);

        return "success add weather";
    }
}
