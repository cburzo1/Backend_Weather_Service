package com.chrisweatherproject.weatherproject.controller;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/add")
    public String addWeather(/*@RequestBody WeatherDTO weatherDTO*/){
        weatherService.addWeather(/*weatherDTO*/);

        //System.out.println(weatherDTO);

        return "success add weather";
    }

    @GetMapping
    public List<Weather> getWeather(){
        return weatherService.getWeather();
    }

    @GetMapping("/get")
    public Weather getWeather(@RequestParam Integer id){
        return weatherService.getWeather(id);
    }

    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateWeather(@PathVariable Integer id, @RequestBody Weather weather){
        weatherService.updateWeather(id, weather);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Integer id){
        weatherService.deleteWeather(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-city-name/{id}")
    public ResponseEntity<Void> updateCityName(@PathVariable Integer id, @RequestBody WeatherDTO weatherDTO ){
        weatherService.updateCityName(id, weatherDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dp")
    public void getWeatherData(){
        return weatherService.getWeatherData();
    }*/
}
