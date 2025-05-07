package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public Weather getWeather(Integer id){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));
        return weather;
    }

    @Override
    public void updateWeather(Integer id, Weather weather){
        //check if the object of the given id is in the database
        weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setId(id);
        weatherRepository.save(weather);
    }

    @Override
    public void deleteWeather(Integer id){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weatherRepository.delete(weather);
    }

    @Override
    public void updateCityName(Integer id, WeatherDTO weatherDTO){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setCity_name(weatherDTO.getCity_name());

        weatherRepository.save(weather);
    }
}
