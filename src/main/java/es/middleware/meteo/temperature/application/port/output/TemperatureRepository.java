package es.middleware.meteo.temperature.application.port.output;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface TemperatureRepository {

    Optional<Temperature> findById(double latitude, double longitude);

    Optional<Temperature> save(double latitude, double longitude, Temperature temperature);

    void deleteById(double latitude, double longitude);
}
