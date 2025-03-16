package es.middleware.meteo.temperature.infrastructure.outbound.rest;

import es.middleware.meteo.common.cache.ForecastCacheable;
import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.ConfigurationProperties;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.mapper.TemperatureRestMapper;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.model.Forecast;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class OpenMeteoRestClient implements TemperatureProvider {

    private final RestClient restClient;

    private final ConfigurationProperties configurationProperties;

    public OpenMeteoRestClient(RestClient restClient, ConfigurationProperties configurationProperties) {
        this.restClient = restClient;
        this.configurationProperties = configurationProperties;
    }

    @ForecastCacheable(value = "temperature")
    @Override
    public Optional<Temperature> getCurrentTemperature(Double latitude, Double longitude) {

        return Optional.ofNullable(restClient.get()
                .uri(configurationProperties.temperatureProviderUri()
                        + "?latitude=" + latitude
                        + "&longitude=" + longitude
                        + "&current=temperature_2m")
                .retrieve()
                .body(Forecast.class)).map(TemperatureRestMapper::mapToDomain);

    }

}
