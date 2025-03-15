package es.middleware.meteo.temperature.infrastructure;

import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import es.middleware.meteo.temperature.application.port.input.SaveCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.application.service.CurrentTemperatureCacheService;
import es.middleware.meteo.temperature.application.service.CurrentTemperatureDeleteService;
import es.middleware.meteo.temperature.application.service.CurrentTemperatureQueryService;
import es.middleware.meteo.temperature.application.service.CurrentTemperatureSaveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;

@PropertySource("classpath:meteo-configuration.properties")
@Configuration
public class BeanConfiguration {

    @Value("${mongo.cache.temperature.max.millis}")
    private Integer mongoCacheTemperatureMaxMillis;

    @Value("${temperature.provider.forecast}")
    private String temperatureProviderForecastURl;


    @Bean
    ConfigurationProperties configurationProperties() {
        return new ConfigurationProperties(temperatureProviderForecastURl, mongoCacheTemperatureMaxMillis);
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public CurrentTemperatureQueryService getCurrentTemperature(TemperatureProvider temperatureProvider,
                                                       GetCurrentTemperatureCache getCurrentTemperatureCache,
                                                       SaveCurrentTemperature saveCurrentTemperature) {
        return new CurrentTemperatureQueryService(temperatureProvider, getCurrentTemperatureCache, saveCurrentTemperature);
    }

    @Bean
    public CurrentTemperatureSaveService saveCurrentTemperature(TemperatureRepository temperatureRepository) {
        return new CurrentTemperatureSaveService(temperatureRepository);
    }

    @Bean
    public CurrentTemperatureDeleteService deleteCurrentTemperature(TemperatureRepository temperatureRepository) {
        return new CurrentTemperatureDeleteService(temperatureRepository);
    }

    @Bean
    public CurrentTemperatureCacheService currentTemperatureCacheService(TemperatureRepository temperatureRepository,
                                                                         ConfigurationProperties configurationProperties) {
        return new CurrentTemperatureCacheService(temperatureRepository, configurationProperties);
    }



}
