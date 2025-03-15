package es.middleware.meteo.temperature.infrastructure.outbound.rest.mapper;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.model.Forecast;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TemperatureRestMapper {
    private TemperatureRestMapper() {
    }

    public static Temperature mapToDomain(Forecast forecast) {

        return new Temperature(forecast.getLatitude(),
                forecast.getLongitude(),
                forecast.getCurrent().getTemperature(),
                forecast.getCurrentUnits().getTemperature(),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}
