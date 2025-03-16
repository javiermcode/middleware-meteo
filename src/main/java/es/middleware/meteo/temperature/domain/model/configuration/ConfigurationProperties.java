package es.middleware.meteo.temperature.domain.model.configuration;

public record ConfigurationProperties (String temperatureProviderUri,
                                       long mongoCacheTemperatureMaxMillis,
                                       String temperatureKafkaTopicName){}