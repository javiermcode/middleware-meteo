package es.middleware.meteo.temperature.domain.model;

import java.time.LocalDateTime;

public class Temperature {

    private final double latitude;

    private double longitude;

    private double temperature;

    private String temperatureUnit;

    private LocalDateTime createdAt;

    public Temperature(double latitude, double longitude, double temperature, String temperatureUnit, LocalDateTime createdAt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.createdAt = createdAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
