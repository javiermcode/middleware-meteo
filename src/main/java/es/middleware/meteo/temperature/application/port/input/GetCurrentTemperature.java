package es.middleware.meteo.temperature.application.port.input;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface GetCurrentTemperature {

    Optional<Temperature> getCurrentTemperature(double latitude, double longitude);
}
