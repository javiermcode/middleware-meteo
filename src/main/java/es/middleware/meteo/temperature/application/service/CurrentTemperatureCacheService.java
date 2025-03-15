package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.common.util.TimeUtil;
import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.ConfigurationProperties;

import java.util.Optional;

public class CurrentTemperatureCacheService implements GetCurrentTemperatureCache {

    private final TemperatureRepository temperatureRepository;

    private final ConfigurationProperties configurationProperties;

    public CurrentTemperatureCacheService(TemperatureRepository temperatureRepository,
                                          ConfigurationProperties configurationProperties) {
        this.temperatureRepository = temperatureRepository;
        this.configurationProperties = configurationProperties;
    }

    public Optional<Temperature> getCurrentTemperatureFromCache(double latitude, double longitude) {

        final var temperatureByLatitudeLongitude = temperatureRepository.findById(latitude, longitude);
        if (temperatureByLatitudeLongitude.isPresent()
                && (TimeUtil.getMillisElapsed(temperatureByLatitudeLongitude.get().getCreatedAt())
                    < configurationProperties.mongoCacheTemperatureMaxMillis())) {
            return temperatureByLatitudeLongitude;
        }
        return Optional.empty();
    }

    public Optional<Temperature> getCurrentTemperatureFromCacheIncludingExpired(double latitude, double longitude) {
        return temperatureRepository.findById(latitude, longitude);
    }
}
