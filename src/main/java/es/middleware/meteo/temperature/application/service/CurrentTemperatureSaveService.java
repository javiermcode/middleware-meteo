package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.SaveCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public class CurrentTemperatureSaveService implements SaveCurrentTemperature {

    private final TemperatureRepository temperatureRepository;

    public CurrentTemperatureSaveService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public Optional<Temperature> saveCurrentTemperature(double latitude, double longitude, Temperature temperature) {
        return temperatureRepository.save(latitude, longitude, temperature);
    }
}
