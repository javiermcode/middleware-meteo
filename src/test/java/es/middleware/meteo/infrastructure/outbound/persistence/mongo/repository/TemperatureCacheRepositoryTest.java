package es.middleware.meteo.infrastructure.outbound.persistence.mongo.repository;

import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.mapper.TemperatureCacheMapper;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.repository.TemperatureCacheMongoRepository;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.repository.TemperatureCacheRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TemperatureCacheRepositoryTest {

    @Autowired
    TemperatureCacheRepository temperatureCacheRepository;

    @Autowired
    TemperatureCacheMongoRepository temperatureCacheMongoRepository;

    @Test
    void findById() {
        final var temperatureCacheSample = TemperatureCacheRepositoryTestDataset.temperatureCache.get(0);

        final double latitudeKey = temperatureCacheSample.getId().getLatitude();
        final double longitudeKey = temperatureCacheSample.getId().getLongitude();
        final double latitude = temperatureCacheSample.getLatitude();
        final double longitude = temperatureCacheSample.getLongitude();
        final double temperature = temperatureCacheSample.getTemperature();
        final String temperatureUnit= temperatureCacheSample.getTemperatureUnit();
        final LocalDateTime createdAt = temperatureCacheSample.getCreatedAt();

        temperatureCacheMongoRepository.deleteById(new TemperatureCacheId(latitudeKey, longitudeKey));

        temperatureCacheMongoRepository.save(TemperatureCacheMapper.mapToCache(latitudeKey, longitudeKey,
                        new Temperature(latitude, longitude, temperature, temperatureUnit, createdAt)));

        final var cachedTemperature = temperatureCacheRepository.findById(latitudeKey, longitudeKey);
        assertTrue(cachedTemperature.isPresent());
        assertEquals(latitude, cachedTemperature.get().getLatitude());
        assertEquals(longitude, cachedTemperature.get().getLongitude());
        assertEquals(temperature, cachedTemperature.get().getTemperature());
        assertEquals(temperatureUnit, cachedTemperature.get().getTemperatureUnit());
        assertEquals(createdAt, cachedTemperature.get().getCreatedAt());
    }

    @Test
    void save() {
        final var temperatureCacheSample = TemperatureCacheRepositoryTestDataset.temperatureCache.get(0);

        final double latitudeKey = temperatureCacheSample.getId().getLatitude();
        final double longitudeKey = temperatureCacheSample.getId().getLongitude();
        final double latitude = temperatureCacheSample.getLatitude();
        final double longitude = temperatureCacheSample.getLongitude();
        final double temperature = temperatureCacheSample.getTemperature();
        final String temperatureUnit= temperatureCacheSample.getTemperatureUnit();
        final LocalDateTime createdAt = temperatureCacheSample.getCreatedAt();

        temperatureCacheMongoRepository.deleteById(new TemperatureCacheId(latitudeKey, longitudeKey));

        final var cachedTemperature = temperatureCacheRepository.save(latitudeKey, longitudeKey,
                new Temperature(latitude, longitude, temperature, temperatureUnit, createdAt));

        assertTrue(cachedTemperature.isPresent());
        assertEquals(latitude, cachedTemperature.get().getLatitude());
        assertEquals(longitude, cachedTemperature.get().getLongitude());
        assertEquals(temperature, cachedTemperature.get().getTemperature());
        assertEquals(temperatureUnit, cachedTemperature.get().getTemperatureUnit());
        assertEquals(createdAt, cachedTemperature.get().getCreatedAt());

        final var cachedTemperatureFromRepository = temperatureCacheMongoRepository.findById(new TemperatureCacheId(latitudeKey, longitudeKey));

        assertTrue(cachedTemperatureFromRepository.isPresent());
        assertEquals(latitude, cachedTemperatureFromRepository.get().getLatitude());
        assertEquals(longitude, cachedTemperatureFromRepository.get().getLongitude());
        assertEquals(temperature, cachedTemperatureFromRepository.get().getTemperature());
        assertEquals(temperatureUnit, cachedTemperatureFromRepository.get().getTemperatureUnit());
        assertEquals(createdAt, cachedTemperatureFromRepository.get().getCreatedAt());
    }

    @Test
    void deleteById() {
        final var temperatureCacheSample = TemperatureCacheRepositoryTestDataset.temperatureCache.get(0);

        final double latitudeKey = temperatureCacheSample.getId().getLatitude();
        final double longitudeKey = temperatureCacheSample.getId().getLongitude();
        final double latitude = temperatureCacheSample.getLatitude();
        final double longitude = temperatureCacheSample.getLongitude();
        final double temperature = temperatureCacheSample.getTemperature();
        final String temperatureUnit= temperatureCacheSample.getTemperatureUnit();
        final LocalDateTime createdAt = temperatureCacheSample.getCreatedAt();

        temperatureCacheMongoRepository.save(TemperatureCacheMapper.mapToCache(latitudeKey, longitudeKey, new Temperature(latitude, longitude, temperature, temperatureUnit, createdAt)));

        temperatureCacheRepository.deleteById(latitudeKey, longitudeKey);

        assertTrue(temperatureCacheMongoRepository.findById(new TemperatureCacheId(latitudeKey, longitudeKey)).isEmpty());
    }
}
