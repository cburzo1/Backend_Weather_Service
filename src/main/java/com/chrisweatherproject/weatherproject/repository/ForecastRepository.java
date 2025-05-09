package com.chrisweatherproject.weatherproject.repository;

import com.chrisweatherproject.weatherproject.model.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<Forecast, Integer> {


}
