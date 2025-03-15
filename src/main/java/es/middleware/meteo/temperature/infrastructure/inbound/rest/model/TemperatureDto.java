package es.middleware.meteo.temperature.infrastructure.inbound.rest.model;

public record TemperatureDto(double latitude, double longitude, double temperature, String temperatureUnit){}
