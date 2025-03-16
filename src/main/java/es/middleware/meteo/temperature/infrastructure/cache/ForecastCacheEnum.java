package es.middleware.meteo.temperature.infrastructure.cache;

public enum ForecastCacheEnum {
    TEMPERATURE("temperature");
    public final String value;
    private ForecastCacheEnum(String value) {
        this.value = value;
    }
}