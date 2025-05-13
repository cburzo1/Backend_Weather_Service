package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.ForecastResponseDTO;
import com.chrisweatherproject.weatherproject.model.Forecast;
import com.chrisweatherproject.weatherproject.repository.ForecastRepository;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.chrisweatherproject.weatherproject.dto.ForecastDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//This class implements the established services
@Service
public class ForecastServiceImpl implements ForecastService{

    //Instantiations
    private final ForecastRepository forecastRepository;
    private final RestTemplate restTemplate;

    public ForecastServiceImpl(ForecastRepository forecastRepository, RestTemplate restTemplate) {
        this.forecastRepository = forecastRepository;
        this.restTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    // This method maps my DTO from an external API to my own database
    public Forecast mapToEntity(ForecastDTO dto, String cityName) {
        Forecast forecast = new Forecast();
        logger.info("Mapping forecast for city: {}", cityName);

        forecast.setCityName(cityName);
        logger.debug("Set city name: {}", cityName);

        if (dto.getMain() != null) {
            forecast.setTemperature(dto.getMain().getTemp());
            forecast.setFeelsLike(dto.getMain().getFeelsLike());
            forecast.setHumidity(dto.getMain().getHumidity());
            forecast.setPressure(dto.getMain().getPressure());
            logger.debug("Mapped main data: temperature = {}, feelsLike = {}, humidity = {}, pressure = {}",
                    forecast.getTemperature(), forecast.getFeelsLike(), forecast.getHumidity(), forecast.getPressure());
        } else {
            logger.warn("Main weather data is null.");
        }

        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            forecast.setDescription(dto.getWeather().get(0).getDescription());
            forecast.setIcon(dto.getWeather().get(0).getIcon());
            logger.debug("Mapped weather data: description = {}, icon = {}",
                    forecast.getDescription(), forecast.getIcon());
        } else {
            logger.warn("Weather data is empty or null.");
        }

        if (dto.getWind() != null) {
            forecast.setWindSpeed(dto.getWind().getSpeed());
            forecast.setWindDegree(dto.getWind().getDeg());
            logger.debug("Mapped wind data: speed = {}, degree = {}", forecast.getWindSpeed(), forecast.getWindDegree());
        } else {
            logger.warn("Wind data is null.");
        }

        forecast.setVisibility(dto.getVisibility());
        forecast.setPop(dto.getPop());
        logger.debug("Mapped visibility = {}, pop = {}", forecast.getVisibility(), forecast.getPop());

        if (dto.getForecastTime() != null && !dto.getForecastTime().isBlank()) {
            try {
                forecast.setForecastTime(LocalDateTime.parse(
                        dto.getForecastTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
                logger.debug("Mapped forecast time: {}", forecast.getForecastTime());
            } catch (DateTimeParseException e) {
                logger.error("Could not parse forecast time: {}", dto.getForecastTime(), e);
            }
        } else {
            logger.warn("Forecast time is null or blank.");
        }

        return forecast;
    }

    //This method sets and saves the forecast data for 3 cities every 10 minutes
    @Override
    @Scheduled(fixedRate = 600000)
    public void addForecast() {
        logger.info("Starting forecast fetch and save process.");

        String urlNY = "https://api.openweathermap.org/data/2.5/forecast?q=New York City&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlMi = "https://api.openweathermap.org/data/2.5/forecast?q=Miami&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlPh = "https://api.openweathermap.org/data/2.5/forecast?q=Phoenix&appid=f22099277e7c5b092f838a7218ea4c6e";

        try {
            ForecastResponseDTO forecastresponseDTO_NY = restTemplate.getForObject(urlNY, ForecastResponseDTO.class);
            processForecastResponse(forecastresponseDTO_NY, "New York City");

            ForecastResponseDTO forecastresponseDTO_Mi = restTemplate.getForObject(urlMi, ForecastResponseDTO.class);
            processForecastResponse(forecastresponseDTO_Mi, "Miami");

            ForecastResponseDTO forecastresponseDTO_Ph = restTemplate.getForObject(urlPh, ForecastResponseDTO.class);
            processForecastResponse(forecastresponseDTO_Ph, "Phoenix");

            logger.info("Finished forecast fetch and save process.");
        } catch (Exception e) {
            logger.error("Exception occurred while fetching or saving forecast data.", e);
        }
    }

    //List wrapper, as all forecasts are lists at their root, I need to iterate through each forecast for each day
    // of the 5 days
    private void processForecastResponse(ForecastResponseDTO response, String cityName) {
        if (response != null && response.getList() != null) {
            logger.info("Received forecast data for {}", cityName);
            for (ForecastDTO dto : response.getList()) {
                Forecast forecast = mapToEntity(dto, cityName);
                forecastRepository.save(forecast);
                logger.debug("Saved forecast record for city: {}", cityName);
            }
        } else {
            logger.warn("No forecast data received for {}", cityName);
        }
    }
}
