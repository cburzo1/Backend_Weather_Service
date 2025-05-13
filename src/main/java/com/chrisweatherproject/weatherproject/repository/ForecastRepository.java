package com.chrisweatherproject.weatherproject.repository;

import com.chrisweatherproject.weatherproject.model.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

//Repos provide important database methods like save, find, delete etc.
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {


}
