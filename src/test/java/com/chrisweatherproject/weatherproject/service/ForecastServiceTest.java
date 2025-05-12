package com.chrisweatherproject.weatherproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import com.chrisweatherproject.weatherproject.dto.ForecastDTO;
import com.chrisweatherproject.weatherproject.dto.ForecastResponseDTO;
import com.chrisweatherproject.weatherproject.model.Forecast;
import com.chrisweatherproject.weatherproject.repository.ForecastRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ForecastRepository forecastRepository;

    @InjectMocks
    private ForecastServiceImpl forecastService;

    @Test
    public void testAddForecast_NewYorkOnly() {
        ForecastDTO dto1 = new ForecastDTO();
        ForecastDTO dto2 = new ForecastDTO();
        List<ForecastDTO> nyList = List.of(dto1, dto2);

        ForecastResponseDTO mockResponse = new ForecastResponseDTO();
        mockResponse.setList(nyList);

        when(restTemplate.getForObject(contains("New York City"), eq(ForecastResponseDTO.class)))
                .thenReturn(mockResponse);

        when(restTemplate.getForObject(contains("Miami"), eq(ForecastResponseDTO.class)))
                .thenReturn(null);
        when(restTemplate.getForObject(contains("Phoenix"), eq(ForecastResponseDTO.class)))
                .thenReturn(null);

        forecastService.addForecast();

        verify(forecastRepository, times(nyList.size())).save(any(Forecast.class));
    }

    @Test
    void testMapToEntity_HappyPath() {
        ForecastDTO dto = new ForecastDTO();

        ForecastDTO.MainDTO main = new ForecastDTO.MainDTO();
        main.setTemp(22.5);
        main.setFeelsLike(21.0);
        main.setHumidity(60);
        main.setPressure(1012);
        dto.setMain(main);

        ForecastDTO.WeatherDTO weather = new ForecastDTO.WeatherDTO();
        weather.setDescription("clear sky");
        weather.setIcon("01d");
        dto.setWeather(List.of(weather));

        ForecastDTO.WindDTO wind = new ForecastDTO.WindDTO();
        wind.setSpeed(5.4);
        wind.setDeg(180);
        dto.setWind(wind);

        dto.setVisibility(10000);
        dto.setPop(0.2);
        dto.setForecastTime("2025-05-12 15:00:00");

        String cityName = "New York";
        Forecast result = forecastService.mapToEntity(dto, cityName);

        assertEquals("New York", result.getCityName());
        assertEquals(22.5, result.getTemperature());
        assertEquals(21.0, result.getFeelsLike());
        assertEquals(60, result.getHumidity());
        assertEquals(1012, result.getPressure());
        assertEquals("clear sky", result.getDescription());
        assertEquals("01d", result.getIcon());
        assertEquals(5.4, result.getWindSpeed());
        assertEquals(180, result.getWindDegree());
        assertEquals(10000, result.getVisibility());
        assertEquals(0.2, result.getPop());
        assertEquals(LocalDateTime.of(2025, 5, 12, 15, 0, 0), result.getForecastTime());
    }

    @Test
    void testMapToEntity_NullNestedDTOs() {
        ForecastDTO dto = new ForecastDTO();
        dto.setMain(null);
        dto.setWeather(null);
        dto.setWind(null);
        dto.setVisibility(0);
        dto.setPop(0);
        dto.setForecastTime(null);

        String cityName = "Paris";
        Forecast result = forecastService.mapToEntity(dto, cityName);

        assertEquals("Paris", result.getCityName());
        assertEquals(0.0, result.getTemperature());
        assertEquals(0.0, result.getFeelsLike());
        assertEquals(0.0, result.getHumidity());
        assertEquals(0.0, result.getPressure());
        assertNull(result.getDescription());
        assertNull(result.getIcon());
        assertEquals(0.0, result.getWindSpeed());
        assertEquals(0.0, result.getWindDegree());
        assertEquals(0.0, result.getVisibility());
        assertEquals(0.0, result.getPop());
        assertNull(result.getForecastTime());
    }

    @Test
    void testMapToEntity_whenMainIsNull_shouldHandleGracefully() {
        ForecastDTO dto = new ForecastDTO();
        ForecastDTO.WeatherDTO weather = new ForecastDTO.WeatherDTO();
        weather.setDescription("Clear sky");
        weather.setIcon("01d");
        dto.setWeather(List.of(weather));
        ForecastDTO.WindDTO wind = new ForecastDTO.WindDTO();
        wind.setSpeed(5.5);
        wind.setDeg(270);
        dto.setWind(wind);
        dto.setVisibility(10000);
        dto.setPop(0.2);
        dto.setForecastTime("2024-05-12 15:00:00");

        Forecast result = forecastService.mapToEntity(dto, "New York");

        assertEquals("New York", result.getCityName());
        assertEquals(0.0, result.getTemperature());
        assertEquals(0.0, result.getFeelsLike());
        assertEquals(0.0, result.getHumidity());
        assertEquals(0.0, result.getPressure());

        assertEquals("Clear sky", result.getDescription());
        assertEquals("01d", result.getIcon());
        assertEquals(5.5, result.getWindSpeed());
        assertEquals(270, result.getWindDegree());
        assertEquals(10000, result.getVisibility());
        assertEquals(0.2, result.getPop());
        assertEquals(LocalDateTime.of(2024, 5, 12, 15, 0), result.getForecastTime());
    }

    @Test
    void testMapToEntity_whenWeatherIsNull_shouldNotThrowAndSetDescriptionAndIconToNull() {
        ForecastDTO dto = new ForecastDTO();
        dto.setWeather(null); // explicit null
        dto.setMain(new ForecastDTO.MainDTO());
        dto.setWind(new ForecastDTO.WindDTO());
        dto.setForecastTime("2025-05-12 14:00:00");
        dto.setVisibility(10000);
        dto.setPop(0.3);

        Forecast result = forecastService.mapToEntity(dto, "TestCity");

        assertNotNull(result);
        assertNull(result.getDescription());
        assertNull(result.getIcon());
    }

    @Test
    void testMapToEntity_whenWindIsNull_shouldNotThrowAndSetWindSpeedAndWindDegreeToZero() {

        ForecastDTO dto = new ForecastDTO();
        dto.setWeather(new ArrayList<>());
        dto.setWind(null);
        dto.setMain(new ForecastDTO.MainDTO());
        dto.setForecastTime("2025-05-12 14:00:00");
        dto.setVisibility(10000);
        dto.setPop(0.3);

        Forecast result = forecastService.mapToEntity(dto, "TestCity");

        assertNotNull(result);
        assertEquals(0.0, result.getWindSpeed());
        assertEquals(0.0, result.getWindDegree());
    }

    @Test
    void testMapToEntity_whenForecastTimeIsNullOrBlank_shouldNotThrowAndSetForecastTimeToNull() {
        ForecastDTO dto = new ForecastDTO();
        dto.setWeather(new ArrayList<>());
        dto.setWind(new ForecastDTO.WindDTO());
        dto.setMain(new ForecastDTO.MainDTO());
        dto.setVisibility(10000);
        dto.setPop(0.3);
        dto.setForecastTime(null);

        Forecast result = forecastService.mapToEntity(dto, "TestCity");

        assertNotNull(result);
        assertNull(result.getForecastTime());
    }
}
