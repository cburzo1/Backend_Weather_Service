package com.chrisweatherproject.weatherproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

   /* @Id
    @GeneratedValue
    private Integer id;
    private String city_name;
    private String current_weather;
    private String weather_forecast;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private String description;
    private double temperature;

    private LocalDateTime timestamp;
}
