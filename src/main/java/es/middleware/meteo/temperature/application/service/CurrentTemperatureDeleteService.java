package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.DeleteCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;

public class CurrentTemperatureDeleteService implements DeleteCurrentTemperature {

    private final TemperatureRepository temperatureRepository;

    public CurrentTemperatureDeleteService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public void deleteCurrentTemperature(double latitude, double longitude) {
        temperatureRepository.deleteById(latitude, longitude);
    }
}
