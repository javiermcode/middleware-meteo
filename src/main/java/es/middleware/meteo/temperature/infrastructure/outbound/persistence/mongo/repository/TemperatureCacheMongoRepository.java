package es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.repository;

import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCache;
import es.middleware.meteo.temperature.infrastructure.outbound.persistence.mongo.model.TemperatureCacheId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureCacheMongoRepository extends MongoRepository<TemperatureCache, TemperatureCacheId> {
}
