package es.middleware.meteo.temperature.infrastructure.outbound.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentUnits {

    @JsonProperty("temperature_2m")
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
