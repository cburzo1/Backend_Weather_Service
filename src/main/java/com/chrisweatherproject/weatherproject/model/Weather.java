package com.chrisweatherproject.weatherproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue
    private Integer id;
    private String city_name;
    private String current_weather;
    private String weather_forecast;
}
