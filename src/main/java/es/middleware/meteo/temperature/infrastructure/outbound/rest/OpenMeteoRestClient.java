package es.middleware.meteo.temperature.infrastructure.outbound.rest;

import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.mapper.TemperatureRestMapper;
import es.middleware.meteo.temperature.infrastructure.outbound.rest.model.Forecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
@PropertySource("classpath:temperature-provider.properties")
public class OpenMeteoRestClient implements TemperatureProvider {

    private final RestClient restClient;

    @Value("${temperature.provider.forecast}")
    private String temperatureProviderForecastUrl;

    public OpenMeteoRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Optional<Temperature> getCurrentTemperature(Double latitude, Double longitude) {

        return Optional.ofNullable(restClient.get()
                .uri(temperatureProviderForecastUrl
                        + "?latitude=" + latitude
                        + "&longitude=" + longitude
                        + "&current=temperature_2m")
                .retrieve()
                .body(Forecast.class)).map(TemperatureRestMapper::mapToDomain);

    }

}
