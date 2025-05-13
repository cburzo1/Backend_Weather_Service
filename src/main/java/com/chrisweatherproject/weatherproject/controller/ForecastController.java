package com.chrisweatherproject.weatherproject.controller;

import com.chrisweatherproject.weatherproject.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    //Provides access via .../forecast/add
    @PostMapping("/add")
    public String addForecast(){
        forecastService.addForecast();

        return "success add forecast";
    }
}
