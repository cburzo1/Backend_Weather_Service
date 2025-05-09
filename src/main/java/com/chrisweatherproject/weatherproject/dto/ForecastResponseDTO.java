package com.chrisweatherproject.weatherproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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