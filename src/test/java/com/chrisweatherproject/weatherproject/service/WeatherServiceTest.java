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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testMapToEntity_HappyPath() {
        WeatherDTO dto = new WeatherDTO();

        WeatherDTO.Main main = new WeatherDTO.Main();
        main.setTemp(25.0);
        dto.setMain(main);

        WeatherDTO.Weather weatherInfo = new WeatherDTO.Weather();
        weatherInfo.setDescription("Clear sky");
        dto.setWeather(List.of(weatherInfo));

        dto.setCityName("New York");

        Weather result = weatherService.mapToEntity(dto);

        assertEquals("New York", result.getCityName());
        assertEquals(25.0, result.getTemperature());
        assertEquals("Clear sky", result.getDescription());
    }

    @Test
    void testMapToEntity_NullMain() {
        WeatherDTO dto = new WeatherDTO();
        dto.setMain(null); // explicitly setting it null
        WeatherDTO.Weather weather = new WeatherDTO.Weather();
        weather.setDescription("Clear");
        dto.setWeather(List.of(weather));
        dto.setCityName("Los Angeles");

        Weather result = weatherService.mapToEntity(dto);

        assertNotNull(result);
        assertEquals("Los Angeles", result.getCityName());
        assertEquals(0.0, result.getTemperature()); // because main is null
        assertEquals("Clear", result.getDescription());
    }

    @Test
    void testMapToEntity_EmptyWeather() {
        WeatherDTO dto = new WeatherDTO();
        dto.setWeather(Collections.emptyList()); // Simulate no weather info
        dto.setMain(new WeatherDTO.Main()); // Providing a valid main object for temperature, etc.
        dto.setCityName("Los Angeles");

        Weather result = weatherService.mapToEntity(dto);

        assertNotNull(result);
        assertEquals("Los Angeles", result.getCityName());
        assertNull(result.getDescription()); // Should be null, as no weather info is available
    }

    @Test
    void testMapToEntity_NullWeather() {

        WeatherDTO dto = new WeatherDTO();
        dto.setWeather(null); // Simulate missing weather info
        dto.setMain(new WeatherDTO.Main()); // Valid main object
        dto.setCityName("Los Angeles");

        Weather result = weatherService.mapToEntity(dto);

        assertNotNull(result);
        assertEquals("Los Angeles", result.getCityName());
        assertNull(result.getDescription()); // Should still be null, as weather is missing
    }

    @Test
    void testMapToEntity_NullCityName() {
        WeatherDTO dto = new WeatherDTO();
        dto.setCityName(null);

        WeatherDTO.Weather weather = new WeatherDTO.Weather();
        weather.setDescription("Clear sky");
        dto.setWeather(List.of(weather));

        WeatherDTO.Main main = new WeatherDTO.Main();
        main.setTemp(22.0);
        dto.setMain(main);

        Weather result = weatherService.mapToEntity(dto);

        assertNotNull(result);
        assertNull(result.getCityName()); // Assuming you allow null cityName in your model
        assertEquals(22.0, result.getTemperature());
        assertEquals("Clear sky", result.getDescription());
    }
}
