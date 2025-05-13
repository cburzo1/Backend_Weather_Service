package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

//This class implements the established services
@Service
public class WeatherServiceImpl implements WeatherService{

    //Instantiations
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    public WeatherServiceImpl(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Value("${openweathermap.api.key}")
    private String apiKey;

    // This method maps my DTO from an external API to my own database
    public Weather mapToEntity(WeatherDTO dto) {
        Weather weather = new Weather();
        logger.info("Mapping weather data for city: {}", dto.getCityName());

        weather.setCityName(dto.getCityName());
        logger.debug("Set city name: {}", dto.getCityName());

        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            String description = dto.getWeather().get(0).getDescription();
            weather.setDescription(description);
            logger.debug("Mapped weather description: {}", description);
        } else {
            logger.warn("Weather description data is missing.");
        }

        if (dto.getMain() != null) {
            double temp = dto.getMain().getTemp();
            weather.setTemperature(temp);
            logger.debug("Mapped temperature: {}", temp);
        } else {
            logger.warn("Main temperature data is null.");
        }

        LocalDateTime now = LocalDateTime.now();
        weather.setTimestamp(now);
        logger.debug("Set timestamp: {}", now);

        return weather;
    }

    //This method sets and saves the forecast data for 3 cities every 10 minutes
    @Override
    @Scheduled(fixedRate = 600000)
    public void addWeather() {
        logger.info("Starting to fetch and store current weather data for predefined cities.");

        String urlNY = "https://api.openweathermap.org/data/2.5/weather?q=New York City&appid=" + apiKey;
        String urlMi = "https://api.openweathermap.org/data/2.5/weather?q=Miami&appid="+ apiKey;
        String urlPh = "https://api.openweathermap.org/data/2.5/weather?q=Phoenix&appid="+ apiKey;

        try {
            logger.debug("Fetching weather data for New York City.");
            WeatherDTO weatherDTO_NY = restTemplate.getForObject(urlNY, WeatherDTO.class);
            weatherRepository.save(mapToEntity(weatherDTO_NY));
            logger.info("Saved weather data for New York City.");
        } catch (Exception e) {
            logger.error("Failed to fetch or save weather data for New York City.", e);
        }

        try {
            logger.debug("Fetching weather data for Miami.");
            WeatherDTO weatherDTO_MI = restTemplate.getForObject(urlMi, WeatherDTO.class);
            weatherRepository.save(mapToEntity(weatherDTO_MI));
            logger.info("Saved weather data for Miami.");
        } catch (Exception e) {
            logger.error("Failed to fetch or save weather data for Miami.", e);
        }

        try {
            logger.debug("Fetching weather data for Phoenix.");
            WeatherDTO weatherDTO_PH = restTemplate.getForObject(urlPh, WeatherDTO.class);
            weatherRepository.save(mapToEntity(weatherDTO_PH));
            logger.info("Saved weather data for Phoenix.");
        } catch (Exception e) {
            logger.error("Failed to fetch or save weather data for Phoenix.", e);
        }

        logger.info("Finished fetching and storing weather data.");
    }
}
