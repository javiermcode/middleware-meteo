package es.middleware.meteo.temperature.application.port.input;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface DeleteCurrentTemperature {

    Optional<Temperature> deleteCurrentTemperature(double latitude, double longitude);

    void deleteCurrentTemperatureById(double latitude, double longitude);
}
