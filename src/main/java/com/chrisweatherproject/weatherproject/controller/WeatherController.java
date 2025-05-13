package com.chrisweatherproject.weatherproject.controller;

import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    //Provides access via .../weather/add
    @PostMapping("/add")
    public String addWeather(){
        weatherService.addWeather();

        return "success add weather";
    }
}
