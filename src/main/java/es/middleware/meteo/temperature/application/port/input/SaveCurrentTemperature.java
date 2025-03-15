package es.middleware.meteo.temperature.application.port.input;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface SaveCurrentTemperature {

    Optional<Temperature> saveCurrentTemperature(double latitude, double longitude, Temperature temperature);
}
