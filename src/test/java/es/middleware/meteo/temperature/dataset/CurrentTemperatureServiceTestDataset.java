package es.middleware.meteo.temperature.dataset;

import es.middleware.meteo.temperature.dataset.model.Coordinates;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCache;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

public class CurrentTemperatureServiceTestDataset {

    private static final double LATITUDE_KEY = 30.125;
    private static final double LONGITUDE_KEY = 145.375;

    private static final double INVALID_LATITUDE_KEY = 91;
    private static final double INVALID_LONGITUDE_KEY = -181;

    private static final double LATITUDE = 30.10;
    private static final double LONGITUDE = 145.25;
    private static final double TEMPERATURE = 18.7;
    private static final String TEMPERATURE_UNIT = "ºC";

    private static final double PROVIDER_LATITUDE = 30.10;
    private static final double PROVIDER_LONGITUDE = 145.25;
    private static final double PROVIDER_TEMPERATURE = 18.7;
    private static final String PROVIDER_TEMPERATURE_UNIT = "ºC";


    public static final Supplier<Coordinates> coordinates =
            () -> new Coordinates(LATITUDE_KEY, LONGITUDE_KEY);

    public static final Supplier<Coordinates> invalidCoordinates =
            () -> new Coordinates(INVALID_LATITUDE_KEY, INVALID_LONGITUDE_KEY);

    public static final Supplier<Temperature> mockedCurrentTemperature =
            () -> new Temperature(
                    LATITUDE,
                    LONGITUDE,
                    TEMPERATURE,
                    TEMPERATURE_UNIT,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

    public static final Supplier<Temperature> mockedProviderTemperature =
            () -> new Temperature(
                    PROVIDER_LATITUDE,
                    PROVIDER_LONGITUDE,
                    PROVIDER_TEMPERATURE,
                    PROVIDER_TEMPERATURE_UNIT,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

    public static final Supplier<TemperatureCache> mockedCurrentTemperatureCache =
            () -> new TemperatureCache(new TemperatureCacheId(LATITUDE_KEY, LONGITUDE_KEY),
                    LATITUDE,
                    LONGITUDE,
                    TEMPERATURE,
                    TEMPERATURE_UNIT,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

}
