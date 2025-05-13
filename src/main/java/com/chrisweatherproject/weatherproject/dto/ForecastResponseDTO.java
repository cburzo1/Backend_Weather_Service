package com.chrisweatherproject.weatherproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

//DTO builds the structure for data transfer to external APIs and clients
// They are like models and have appropriate attributes, setters, and getters
public class ForecastResponseDTO {

    @JsonProperty("list")
    private List<ForecastDTO> list;

    public List<ForecastDTO> getList() {
        return list;
    }

    public void setList(List<ForecastDTO> list) {
        this.list = list;
    }
}