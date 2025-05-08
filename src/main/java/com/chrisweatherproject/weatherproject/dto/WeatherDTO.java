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
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        @JsonProperty("description")
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /*public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }*/

}