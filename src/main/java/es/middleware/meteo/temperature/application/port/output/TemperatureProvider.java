package es.middleware.meteo.temperature.application.port.output;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface TemperatureProvider {
    Optional<Temperature> getCurrentTemperature(Double latitude, Double longitude);
}
