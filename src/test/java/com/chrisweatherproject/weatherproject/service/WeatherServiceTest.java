package com.chrisweatherproject.weatherproject.service;

import com.chrisweatherproject.weatherproject.dto.WeatherDTO;
import com.chrisweatherproject.weatherproject.model.Weather;
import com.chrisweatherproject.weatherproject.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void testAddWeather() {
        WeatherDTO mockDto = new WeatherDTO();
        WeatherDTO.Main main = new WeatherDTO.Main();
        main.setTemp(22.0);
        mockDto.setMain(main);
        WeatherDTO.Weather weatherInfo = new WeatherDTO.Weather();
        weatherInfo.setDescription("Sunny");
        mockDto.setWeather(List.of(weatherInfo));
        mockDto.setCityName("New York");

        when(restTemplate.getForObject(anyString(), eq(WeatherDTO.class))).thenReturn(mockDto);

        weatherService.addWeather();

        verify(weatherRepository, atLeastOnce()).save(any(Weather.class));
    }
}
