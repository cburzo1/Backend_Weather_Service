package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Forecast;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.ForecastRepository;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.chrisweatherproject.weatherproject.dto.ForecastDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class ForecastServiceImpl implements ForecastService{
    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;

    public ForecastServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Forecast mapToEntity(ForecastDTO dto) {
        Forecast forecast = new Forecast();

        if (dto.getMain() != null) {
            forecast.setTemperature(dto.getMain().getTemp());
            forecast.setFeelsLike(dto.getMain().getFeelsLike());
            forecast.setHumidity(dto.getMain().getHumidity());
            forecast.setPressure(dto.getMain().getPressure());
        }

        if (dto.getWeather() != null) {
            forecast.setDescription(dto.getWeather().get(0).getDescription());
            forecast.setIcon(dto.getWeather().get(0).getIcon());
        }

        if (dto.getWind() != null) {
            forecast.setWindSpeed(dto.getWind().getSpeed());
            forecast.setWindDegree(dto.getWind().getDeg());
        }

        forecast.setVisibility(dto.getVisibility());
        forecast.setPop(dto.getPop());

        // Parsing forecastTime string (e.g., "2025-05-08 12:00:00") into LocalDateTime
        if (dto.getForecastTime() != null && !dto.getForecastTime().isBlank()) {
            try {
                forecast.setForecastTime(LocalDateTime.parse(
                        dto.getForecastTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
            } catch (DateTimeParseException e) {
                System.err.println("Could not parse forecast time: " + dto.getForecastTime());
                e.printStackTrace();
            }
        }

        return forecast;
    }

    @Override
    public void addForecast(){
        System.out.println("HELLO?");
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";

        ForecastDTO forecastDTO = restTemplate.getForObject(url, ForecastDTO.class);

        forecastRepository.save(mapToEntity(forecastDTO));
    }
}
