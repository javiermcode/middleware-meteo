package es.middleware.meteo.temperature.infrastructure.outbound.event.kafka.mapper;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.event.kafka.model.EventTemperatureDto;

import java.util.Optional;

public class EventTemperatureDtoMapper {
    private EventTemperatureDtoMapper() {
    }

    public static Optional<EventTemperatureDto> mapToDto(Temperature temperature) {
        return Optional.of( new EventTemperatureDto(temperature.getLatitude(),
                temperature.getLongitude(),
                temperature.getTemperature(),
                temperature.getTemperatureUnit()));
    }
}
