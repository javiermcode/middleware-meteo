package es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "temperature")
public class TemperatureCache {

        @Id
        private TemperatureCacheId id;

        private double latitude;

        private double longitude;

        private double temperature;

        private String temperatureUnit;

        private LocalDateTime createdAt;


        public TemperatureCache(TemperatureCacheId id, double latitude, double longitude, double temperature,
                                String temperatureUnit, LocalDateTime createdAt) {
                this.id = id;
                this.latitude = latitude;
                this.longitude = longitude;
                this.temperature = temperature;
                this.temperatureUnit = temperatureUnit;
                this.createdAt = createdAt;
        }

        public TemperatureCacheId getId() {
                return id;
        }

        public void setId(TemperatureCacheId id) {
                this.id = id;
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

        public double getTemperature() {
                return temperature;
        }

        public void setTemperature(double temperature) {
                this.temperature = temperature;
        }

        public String getTemperatureUnit() {
                return temperatureUnit;
        }

        public void setTemperatureUnit(String temperatureUnit) {
                this.temperatureUnit = temperatureUnit;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
