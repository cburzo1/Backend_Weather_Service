package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public void addWeather(Weather weather){
        System.out.println("HELLO?");
        weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getWeather(){
        return weatherRepository.findAll();
    }
}
