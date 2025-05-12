package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Weather mapToEntity(WeatherDTO dto) {
        Weather weather = new Weather();
        weather.setCityName(dto.getCityName());
        weather.setDescription(dto.getWeather().get(0).getDescription());
        weather.setTemperature(dto.getMain().getTemp());
        weather.setTimestamp(LocalDateTime.now());
        return weather;
    }

    @Override
    //@Scheduled(fixedRate = 600000)
    public void addWeather(){
        String urlNY = "https://api.openweathermap.org/data/2.5/weather?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlMi = "https://api.openweathermap.org/data/2.5/weather?q=" + "Miami" + "&appid=f22099277e7c5b092f838a7218ea4c6e";
        String urlPh = "https://api.openweathermap.org/data/2.5/weather?q=" + "Phoenix" + "&appid=f22099277e7c5b092f838a7218ea4c6e";


        WeatherDTO weatherDTO_NY = restTemplate.getForObject(urlNY, WeatherDTO.class);
        WeatherDTO weatherDTO_MI = restTemplate.getForObject(urlMi, WeatherDTO.class);
        WeatherDTO weatherDTO_PH = restTemplate.getForObject(urlPh, WeatherDTO.class);

        weatherRepository.save(mapToEntity(weatherDTO_NY));
        weatherRepository.save(mapToEntity(weatherDTO_MI));
        weatherRepository.save(mapToEntity(weatherDTO_PH));
    }

    @Override
    public List<Weather> getWeather(){
        return weatherRepository.findAll();
    }

    @Override
    public Weather getWeather(Integer id){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";

        return weather;
    }
}
