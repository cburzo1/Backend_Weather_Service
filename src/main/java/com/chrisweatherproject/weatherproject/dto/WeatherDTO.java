package com.chrisweatherproject.weatherproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    //private String city_name;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("dt")
    private long timestamp;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private List<Weather> weather;

        // getters, setters

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {
        @JsonProperty("temp")
        private double temp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        @JsonProperty("description")
        private String description;
    }

    /*public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }*/

}