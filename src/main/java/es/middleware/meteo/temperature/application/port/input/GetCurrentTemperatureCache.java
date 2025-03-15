package es.middleware.meteo.temperature.application.port.input;

import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public interface GetCurrentTemperatureCache {

    Optional<Temperature> getCurrentTemperatureFromCache(double latitude, double longitude);

    Optional<Temperature> getCurrentTemperatureFromCacheIncludingExpired(double latitude, double longitude);
}
