package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public class CurrentTemperatureQueryService implements GetCurrentTemperature {

    private final TemperatureProvider temperatureProvider;

    public CurrentTemperatureQueryService(TemperatureProvider temperatureProvider) {
        this.temperatureProvider = temperatureProvider;
    }

    @Override
    public Optional<Temperature> getCurrentTemperature(double latitude, double longitude) {
        return temperatureProvider.getCurrentTemperature(latitude, longitude);
    }
}
