package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
import es.middleware.meteo.temperature.domain.model.configuration.ConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class CurrentTemperatureCacheServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @Mock
    private ConfigurationProperties configurationProperties;

    @InjectMocks
    private CurrentTemperatureCacheService currentTemperatureCacheService;

    @Test
    void testGetCurrentTemperatureFromCache() {

        final var temperatureSample = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        when(temperatureRepository.findById(temperatureSample.latitude(), temperatureSample.longitude()))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        when(configurationProperties.mongoCacheTemperatureMaxMillis()).thenReturn(1000L);

        final var currentTemperatureFromCache = currentTemperatureCacheService.getCurrentTemperatureFromCache(
                temperatureSample.latitude(), temperatureSample.longitude());

        assertTrue(currentTemperatureFromCache.isPresent());
        assertEquals(mockedCurrentTemperature.getLatitude(), currentTemperatureFromCache.get().getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), currentTemperatureFromCache.get().getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), currentTemperatureFromCache.get().getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), currentTemperatureFromCache.get().getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), currentTemperatureFromCache.get().getCreatedAt());
    }

    @Test
    void testGetCurrentTemperatureFromCacheIncludingExpired() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        when(temperatureRepository.findById(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        when(configurationProperties.mongoCacheTemperatureMaxMillis()).thenReturn(1000L);

        final var currentTemperatureFromCache = currentTemperatureCacheService.getCurrentTemperatureFromCacheIncludingExpired(
                coordinates.latitude(), coordinates.longitude());

        assertTrue(currentTemperatureFromCache.isPresent());
        assertEquals(mockedCurrentTemperature.getLatitude(), currentTemperatureFromCache.get().getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), currentTemperatureFromCache.get().getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), currentTemperatureFromCache.get().getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), currentTemperatureFromCache.get().getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), currentTemperatureFromCache.get().getCreatedAt());

    }



}
