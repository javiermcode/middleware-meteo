package es.middleware.meteo.temperature.infrastructure.outbound.rest.mapper;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.model.Forecast;

import java.time.LocalDateTime;

public class TemperatureRestMapper {

    public static Temperature mapToDomain(Forecast forecast) {

        return new Temperature(forecast.getLatitude(),
                forecast.getLongitude(),
                forecast.getCurrent().getTemperature_2m(),
                forecast.getCurrent_units().getTemperature_2m(),
                LocalDateTime.now());
    }
}
