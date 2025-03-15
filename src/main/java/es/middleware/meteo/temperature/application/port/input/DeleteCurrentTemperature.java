package es.middleware.meteo.temperature.application.port.input;

public interface DeleteCurrentTemperature {

    void deleteCurrentTemperature(double latitude, double longitude);
}
