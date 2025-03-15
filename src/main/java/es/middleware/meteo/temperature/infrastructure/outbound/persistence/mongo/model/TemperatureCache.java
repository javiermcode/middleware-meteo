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
