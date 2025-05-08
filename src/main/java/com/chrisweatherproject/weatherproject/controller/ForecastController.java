package com.chrisweatherproject.weatherproject.controller;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.service.ForecastService;
import com.chrisweatherproject.weatherproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    @PostMapping("/add")
    public String addForecast(){
        forecastService.addForecast(/*weatherDTO*/);

        //System.out.println(weatherDTO);

        return "success add forecast";
    }

    /*@GetMapping
    public List<Weather> getWeather(){
        return weatherService.getWeather();
    }

    @GetMapping("/get")
    public Weather getWeather(@RequestParam Integer id){
        return weatherService.getWeather(id);
    } */
}
