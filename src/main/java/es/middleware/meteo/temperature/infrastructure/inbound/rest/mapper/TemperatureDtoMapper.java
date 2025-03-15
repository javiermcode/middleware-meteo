package es.middleware.meteo.temperature.infrastructure.inbound.rest.mapper;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.inbound.rest.model.TemperatureDto;

public class TemperatureDtoMapper {
    private TemperatureDtoMapper() {
    }

    public static TemperatureDto mapToDto(Temperature temperature) {

        return new TemperatureDto(temperature.getLatitude(),
                temperature.getLongitude(),
                temperature.getTemperature(),
                temperature.getTemperatureUnit());
    }
}
