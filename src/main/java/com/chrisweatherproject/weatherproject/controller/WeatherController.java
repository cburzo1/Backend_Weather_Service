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

    @PostMapping("/add")
    public String addWeather(@RequestBody Weather weather){
        weatherService.addWeather(weather);

        System.out.println(weather);

        return "success add weather";
    }

    @GetMapping()
    public List<Weather> getWeather(){
        return weatherService.getWeather();
    }
}
