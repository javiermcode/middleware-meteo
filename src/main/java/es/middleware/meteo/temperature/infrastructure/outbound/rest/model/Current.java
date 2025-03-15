package es.middleware.meteo.temperature.infrastructure.outbound.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Current {

    @JsonProperty("temperature_2m")
    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
