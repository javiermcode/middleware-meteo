package es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model;

public class TemperatureCacheId {

    private double latitude;

    private double longitude;

    public TemperatureCacheId(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TemperatureCacheId() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
