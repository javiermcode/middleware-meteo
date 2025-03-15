package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperature;
import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import es.middleware.meteo.temperature.application.port.input.SaveCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.domain.model.Temperature;

import java.util.Optional;

public class CurrentTemperatureQueryService implements GetCurrentTemperature {

    private final TemperatureProvider temperatureProvider;

    private final GetCurrentTemperatureCache getCurrentTemperatureCache;

    private final SaveCurrentTemperature saveCurrentTemperature;

    public CurrentTemperatureQueryService(TemperatureProvider temperatureProvider,
                                          GetCurrentTemperatureCache getCurrentTemperatureCache,
                                          SaveCurrentTemperature saveCurrentTemperature) {
        this.temperatureProvider = temperatureProvider;
        this.getCurrentTemperatureCache = getCurrentTemperatureCache;
        this.saveCurrentTemperature = saveCurrentTemperature;
    }

    @Override
    public Optional<Temperature> getCurrentTemperature(double latitude, double longitude) {

        final var currentTemperatureCache = getCurrentTemperatureCache.getCurrentTemperatureFromCache(latitude, longitude);

        if(currentTemperatureCache.isPresent()) {
            return currentTemperatureCache;
        }

        final var currentTemperature = temperatureProvider.getCurrentTemperature(latitude, longitude);
        currentTemperature.ifPresent(temperature ->
                saveCurrentTemperature.saveCurrentTemperature(latitude, longitude, temperature));

        return currentTemperature;
    }
}
