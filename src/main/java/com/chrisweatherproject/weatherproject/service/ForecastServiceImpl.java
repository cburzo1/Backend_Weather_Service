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

@Service
public class ForecastServiceImpl implements ForecastService{

    private final ForecastRepository forecastRepository;
    private final RestTemplate restTemplate;

    public ForecastServiceImpl(ForecastRepository forecastRepository, RestTemplate restTemplate) {
        this.forecastRepository = forecastRepository;
        this.restTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    /*public Forecast mapToEntity(ForecastDTO dto, String cityName) {
        Forecast forecast = new Forecast();

        forecast.setCityName(cityName);

        if (dto.getMain() != null) {
            forecast.setTemperature(dto.getMain().getTemp());
            forecast.setFeelsLike(dto.getMain().getFeelsLike());
            forecast.setHumidity(dto.getMain().getHumidity());
            forecast.setPressure(dto.getMain().getPressure());
        }

        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            forecast.setDescription(dto.getWeather().get(0).getDescription());
            forecast.setIcon(dto.getWeather().get(0).getIcon());
        }

        if (dto.getWind() != null) {
            forecast.setWindSpeed(dto.getWind().getSpeed());
            forecast.setWindDegree(dto.getWind().getDeg());
        }

        forecast.setVisibility(dto.getVisibility());
        forecast.setPop(dto.getPop());

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
    }*/

    public Forecast mapToEntity(ForecastDTO dto, String cityName) {
        Forecast forecast = new Forecast();
        logger.info("Mapping forecast for city: {}", cityName);

        // Set city name and log the step
        forecast.setCityName(cityName);
        logger.debug("Set city name: {}", cityName);

        // Log when weather main data is being mapped
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

        // Log when weather data is being mapped
        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            forecast.setDescription(dto.getWeather().get(0).getDescription());
            forecast.setIcon(dto.getWeather().get(0).getIcon());
            logger.debug("Mapped weather data: description = {}, icon = {}",
                    forecast.getDescription(), forecast.getIcon());
        } else {
            logger.warn("Weather data is empty or null.");
        }

        // Log when wind data is being mapped
        if (dto.getWind() != null) {
            forecast.setWindSpeed(dto.getWind().getSpeed());
            forecast.setWindDegree(dto.getWind().getDeg());
            logger.debug("Mapped wind data: speed = {}, degree = {}", forecast.getWindSpeed(), forecast.getWindDegree());
        } else {
            logger.warn("Wind data is null.");
        }

        // Set and log visibility and pop
        forecast.setVisibility(dto.getVisibility());
        forecast.setPop(dto.getPop());
        logger.debug("Mapped visibility = {}, pop = {}", forecast.getVisibility(), forecast.getPop());

        // Log and handle the forecast time mapping
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

    @Override
    @Scheduled(fixedRate = 600000)
    public void addForecast(){
        String urlNY = "https://api.openweathermap.org/data/2.5/forecast?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlMi = "https://api.openweathermap.org/data/2.5/forecast?q=" + "Miami" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlPh = "https://api.openweathermap.org/data/2.5/forecast?q=" + "Phoenix" + "&appid=f22099277e7c5b092f838a7218ea4c6e";


        ForecastResponseDTO forecastresponseDTO_NY = restTemplate.getForObject(urlNY, ForecastResponseDTO.class);
        ForecastResponseDTO forecastresponseDTO_Mi = restTemplate.getForObject(urlMi, ForecastResponseDTO.class);
        ForecastResponseDTO forecastresponseDTO_Ph = restTemplate.getForObject(urlPh, ForecastResponseDTO.class);


        if (forecastresponseDTO_NY != null && forecastresponseDTO_NY.getList() != null) {
            for (ForecastDTO dto : forecastresponseDTO_NY.getList()) {
                forecastRepository.save(mapToEntity(dto, "New York City"));
            }
        }

        if (forecastresponseDTO_Mi != null && forecastresponseDTO_Mi.getList() != null) {
            for (ForecastDTO dto : forecastresponseDTO_Mi.getList()) {
                forecastRepository.save(mapToEntity(dto, "Miami"));
            }
        }

        if (forecastresponseDTO_Ph != null && forecastresponseDTO_Ph.getList() != null) {
            for (ForecastDTO dto : forecastresponseDTO_Ph.getList()) {
                forecastRepository.save(mapToEntity(dto, "Phoenix"));
            }
        }
    }
}
