package es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.mapper;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCache;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;

public class TemperatureCacheMapper {

    public static Temperature mapToDomain(TemperatureCache temperatureCache) {

        return new Temperature(temperatureCache.getLatitude(),
                temperatureCache.getLongitude(),
                temperatureCache.getTemperature(),
                temperatureCache.getTemperatureUnit(),
                temperatureCache.getCreatedAt());
    }

    public static TemperatureCache mapToCache(double latitude, double longitude, Temperature temperature) {

        return new TemperatureCache(
                new TemperatureCacheId(latitude, longitude),
                temperature.getLatitude(),
                temperature.getLongitude(),
                temperature.getTemperature(),
                temperature.getTemperatureUnit(),
                temperature.getCreatedAt());
    }
}
