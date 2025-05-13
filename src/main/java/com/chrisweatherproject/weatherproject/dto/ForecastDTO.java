package com.chrisweatherproject.weatherproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//DTO builds the structure for data transfer to external APIs and clients
// They are like models and have appropriate attributes, setters, and getters
public class ForecastDTO {

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public MainDTO getMain() {
        return main;
    }

    public void setMain(MainDTO main) {
        this.main = main;
    }

    public List<WeatherDTO> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDTO> weather) {
        this.weather = weather;
    }

    public WindDTO getWind() {
        return wind;
    }

    public void setWind(WindDTO wind) {
        this.wind = wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }

    // Getters and Setters
    @JsonProperty("dt")
    private long timestamp;

    @JsonProperty("main")
    private MainDTO main;

    @JsonProperty("weather")
    private List<WeatherDTO> weather;

    @JsonProperty("wind")
    private WindDTO wind;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("pop")
    private double pop;

    @JsonProperty("dt_txt")
    private String forecastTime;

    // Inner DTOs
    public static class MainDTO {
        @JsonProperty("temp")
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("humidity")
        private int humidity;

        @JsonProperty("pressure")
        private int pressure;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }

        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }

        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }
    }

    public static class WeatherDTO {
        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class WindDTO {
        @JsonProperty("speed")
        private double speed;

        @JsonProperty("deg")
        private int deg;

        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }

        public int getDeg() { return deg; }
        public void setDeg(int deg) { this.deg = deg; }
    }

}