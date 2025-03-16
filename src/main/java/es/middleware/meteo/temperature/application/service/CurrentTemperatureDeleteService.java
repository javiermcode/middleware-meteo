package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.DeleteCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.cache.ForecastCacheEvict;

import java.util.Optional;

public class CurrentTemperatureDeleteService implements DeleteCurrentTemperature {

    private final TemperatureRepository temperatureRepository;

    public CurrentTemperatureDeleteService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @ForecastCacheEvict(value = "temperature")
    @Override
    public Optional<Temperature> deleteCurrentTemperature(double latitude, double longitude) {
        return Optional.empty();
    }

    @Override
    public void deleteCurrentTemperatureById(double latitude, double longitude) {
        temperatureRepository.deleteById(latitude, longitude);
    }
}
