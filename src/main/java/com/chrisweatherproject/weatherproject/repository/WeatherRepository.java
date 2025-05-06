package com.chrisweatherproject.weatherproject.repository;

import com.chrisweatherproject.weatherproject.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {


}
