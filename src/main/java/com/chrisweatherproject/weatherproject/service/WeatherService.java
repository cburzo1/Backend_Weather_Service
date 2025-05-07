package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.model.Weather;

import java.util.List;

public interface WeatherService {
    void addWeather(Weather weather);

    List<Weather> getWeather();
}
