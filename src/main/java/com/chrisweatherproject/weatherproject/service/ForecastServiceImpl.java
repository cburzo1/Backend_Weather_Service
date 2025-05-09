package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.ForecastResponseDTO;
import com.chrisweatherproject.weatherproject.model.Forecast;
import com.chrisweatherproject.weatherproject.repository.ForecastRepository;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
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
    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;

    public ForecastServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Forecast mapToEntity(ForecastDTO dto, String cityName) {
        Forecast forecast = new Forecast();

        forecast.setCityName(cityName);

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
    @Scheduled(fixedRate = 600000)
    public void addForecast(){
        String urlNY = "https://api.openweathermap.org/data/2.5/forecast?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlMi = "https://api.openweathermap.org/data/2.5/forecast?q=" + "Miami" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlPh = "https://api.openweathermap.org/data/2.5/forecast?q=" + "Phoenix" + "&appid=f22099277e7c5b092f838a7218ea4c6e";


        ForecastResponseDTO forecastresponseDTO_NY = restTemplate.getForObject(urlNY, ForecastResponseDTO.class);
        ForecastResponseDTO forecastresponseDTO_Mi = restTemplate.getForObject(urlMi, ForecastResponseDTO.class);
        ForecastResponseDTO forecastresponseDTO_Ph = restTemplate.getForObject(urlPh, ForecastResponseDTO.class);


        for (ForecastDTO dto : forecastresponseDTO_NY.getList()) {
            forecastRepository.save(mapToEntity(dto, "New York City"));
        }

        for (ForecastDTO dto : forecastresponseDTO_Mi.getList()) {
            forecastRepository.save(mapToEntity(dto, "Miami"));
        }

        for (ForecastDTO dto : forecastresponseDTO_Ph.getList()) {
            forecastRepository.save(mapToEntity(dto, "Phoenix"));
        }
    }
}
