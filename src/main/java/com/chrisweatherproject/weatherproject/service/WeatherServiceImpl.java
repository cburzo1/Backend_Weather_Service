package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /*public void getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";

        System.out.println("HELLOOOOO????!!!");
        System.out.println(restTemplate.getForObject(url, WeatherService.class));

        return (List<Weather>) restTemplate.getForObject(url, WeatherService.class);
    }*/

    @Override
    public void addWeather(/*WeatherDTO weatherDTO*/){
        System.out.println("HELLO?");
        //weatherRepository.save(weather);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + "New York City" + "&appid=f22099277e7c5b092f838a7218ea4c6e";

        WeatherDTO weatherDTO = restTemplate.getForObject(url, WeatherDTO.class);

        weatherRepository.save(mapToEntity(weatherDTO));
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

        System.out.println("HELLOOOOO????!!!");
        System.out.println(restTemplate.getForObject(url, String.class));

        return weather;
    }

    /*@Override
    public void updateWeather(Integer id, Weather weather){
        //check if the object of the given id is in the database
        weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setId(id);
        weatherRepository.save(weather);
    }

    @Override
    public void deleteWeather(Integer id){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weatherRepository.delete(weather);
    }

    @Override
    public void updateCityName(Integer id, WeatherDTO weatherDTO){
        Weather weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setCity_name(weatherDTO.getCity_name());

        weatherRepository.save(weather);
    }*/
}
