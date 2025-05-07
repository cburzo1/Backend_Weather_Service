package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;

import java.util.List;

public interface WeatherService {
    void addWeather(Weather weather);

    List<Weather> getWeather();

    Weather getWeather(Integer id);

    void updateWeather(Integer id, Weather weather);

    void deleteWeather(Integer id);

    void updateCityName(Integer id, WeatherDTO weatherDTO);
}
