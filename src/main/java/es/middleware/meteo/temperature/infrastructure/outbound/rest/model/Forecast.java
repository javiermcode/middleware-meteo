package es.middleware.meteo.temperature.infrastructure.outbound.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Forecast {

    private double latitude;
    private double longitude;

    @JsonProperty("current_units")
    private CurrentUnits currentUnits;

    private Current current;

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

    public CurrentUnits getCurrentUnits() {
        return currentUnits;
    }

    public void setCurrentUnits(CurrentUnits currentUnits) {
        this.currentUnits = currentUnits;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
