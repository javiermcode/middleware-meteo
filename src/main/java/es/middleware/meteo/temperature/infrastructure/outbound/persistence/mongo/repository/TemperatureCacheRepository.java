package es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.repository;

import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.mapper.TemperatureCacheMapper;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureCacheRepository implements TemperatureRepository {

    private final TemperatureCacheMongoRepository temperatureCacheMongoRepository;

    public TemperatureCacheRepository(TemperatureCacheMongoRepository temperatureCacheMongoRepository) {
        this.temperatureCacheMongoRepository = temperatureCacheMongoRepository;
    }

    @Override
    public Optional<Temperature> findById(double latitude, double longitude) {
        return temperatureCacheMongoRepository.findById(new TemperatureCacheId(latitude, longitude))
                .map(TemperatureCacheMapper::mapToDomain);
    }

    @Override
    public Optional<Temperature> save(double latitude, double longitude, Temperature temperature) {
        return Optional.of(temperatureCacheMongoRepository.save(TemperatureCacheMapper.mapToCache(latitude, longitude, temperature)))
                .map(TemperatureCacheMapper::mapToDomain);
    }

    @Override
    public void deleteById(double latitude, double longitude) {
        temperatureCacheMongoRepository.deleteById(new TemperatureCacheId(latitude, longitude));
    }
}
