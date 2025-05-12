package com.chrisweatherproject.weatherproject.service;

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
        // Mock New York forecast list
        ForecastDTO dto1 = new ForecastDTO();
        ForecastDTO dto2 = new ForecastDTO();
        List<ForecastDTO> nyList = List.of(dto1, dto2);

        ForecastResponseDTO mockResponse = new ForecastResponseDTO();
        mockResponse.setList(nyList);

        // Only stub the first call (New York)
        when(restTemplate.getForObject(contains("New York City"), eq(ForecastResponseDTO.class)))
                .thenReturn(mockResponse);

        // Let the other two calls return null to avoid NPEs during the test
        when(restTemplate.getForObject(contains("Miami"), eq(ForecastResponseDTO.class)))
                .thenReturn(null);
        when(restTemplate.getForObject(contains("Phoenix"), eq(ForecastResponseDTO.class)))
                .thenReturn(null);

        // Run the method
        forecastService.addForecast();

        // Verify that save was called only for New York DTOs
        verify(forecastRepository, times(nyList.size())).save(any(Forecast.class));
    }
}
