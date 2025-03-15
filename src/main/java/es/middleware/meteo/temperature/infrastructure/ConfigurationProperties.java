package es.middleware.meteo.temperature.infrastructure;

public record ConfigurationProperties (String temperatureProviderUri, long mongoCacheTemperatureMaxMillis){}