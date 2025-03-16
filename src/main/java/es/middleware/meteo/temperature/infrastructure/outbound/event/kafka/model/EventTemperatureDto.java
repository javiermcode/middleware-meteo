package es.middleware.meteo.temperature.infrastructure.outbound.event.kafka.model;

public record EventTemperatureDto(double latitude, double longitude, double temperature, String temperatureUnit){}
