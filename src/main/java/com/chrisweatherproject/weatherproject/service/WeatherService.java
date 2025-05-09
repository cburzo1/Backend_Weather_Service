package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.model.Weather;

import java.util.List;

public interface WeatherService {
    void addWeather();

    List<Weather> getWeather();

    Weather getWeather(Integer id);
}
