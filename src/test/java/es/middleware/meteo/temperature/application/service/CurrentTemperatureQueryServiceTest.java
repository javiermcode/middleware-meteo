package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.input.SaveCurrentTemperature;
import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class CurrentTemperatureQueryServiceTest {

    @Mock
    private SaveCurrentTemperature saveCurrentTemperature;

    @Mock
    private TemperatureProvider temperatureProvider;

    @Mock
    private GetCurrentTemperatureCache getCurrentTemperatureCache;

    @InjectMocks
    private CurrentTemperatureQueryService currentTemperatureQueryService;

    @Test
    void testGetCurrentTemperatureWhenInCache() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        when(getCurrentTemperatureCache.getCurrentTemperatureFromCache(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        final var currentTemperatureFromCache = currentTemperatureQueryService.getCurrentTemperature(
                coordinates.latitude(), coordinates.longitude());

        assertTrue(currentTemperatureFromCache.isPresent());
        assertEquals(mockedCurrentTemperature.getLatitude(), currentTemperatureFromCache.get().getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), currentTemperatureFromCache.get().getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), currentTemperatureFromCache.get().getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), currentTemperatureFromCache.get().getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), currentTemperatureFromCache.get().getCreatedAt());

    }

    @Test
    void testGetCurrentTemperatureWhenNotInCache() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();
        final var mockedProviderTemperature = CurrentTemperatureServiceTestDataset.mockedProviderTemperature.get();

        when(getCurrentTemperatureCache.getCurrentTemperatureFromCache(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.empty());

        when(temperatureProvider.getCurrentTemperature(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.ofNullable(mockedProviderTemperature));

        when(saveCurrentTemperature.saveCurrentTemperature(coordinates.latitude(), coordinates.longitude(), mockedCurrentTemperature))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        final var currentTemperatureFromCache = currentTemperatureQueryService.getCurrentTemperature(
                coordinates.latitude(), coordinates.longitude());

        assertNotNull(currentTemperatureFromCache);
        assertEquals(mockedCurrentTemperature.getLatitude(), mockedProviderTemperature.getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), mockedProviderTemperature.getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), mockedProviderTemperature.getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), mockedProviderTemperature.getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), mockedProviderTemperature.getCreatedAt());

    }

}
