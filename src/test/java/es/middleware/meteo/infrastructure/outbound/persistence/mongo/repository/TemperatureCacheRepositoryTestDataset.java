package es.middleware.meteo.infrastructure.outbound.persistence.mongo.repository;

import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCache;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TemperatureCacheRepositoryTestDataset {

    public static final List<TemperatureCache> temperatureCache = List.of(
            new TemperatureCache(new TemperatureCacheId(30.12,
                    145.34),
                    30.1,
                    145.32,
                    24.7,
                    "ºC",
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));

}
