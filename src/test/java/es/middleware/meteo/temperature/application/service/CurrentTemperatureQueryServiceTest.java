package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.output.TemperatureProvider;
import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
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
    private TemperatureProvider temperatureProvider;

    @InjectMocks
    private CurrentTemperatureQueryService currentTemperatureQueryService;

    @Test
    void testGetCurrentTemperatureWhenExists() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        when(temperatureProvider.getCurrentTemperature(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        final var currentTemperature= currentTemperatureQueryService.getCurrentTemperature(
                coordinates.latitude(), coordinates.longitude());

        assertTrue(currentTemperature.isPresent());
        assertEquals(mockedCurrentTemperature.getLatitude(), currentTemperature.get().getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), currentTemperature.get().getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), currentTemperature.get().getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), currentTemperature.get().getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), currentTemperature.get().getCreatedAt());

    }

    @Test
    void testGetCurrentTemperatureWhenNotExists() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();

        when(temperatureProvider.getCurrentTemperature(coordinates.latitude(), coordinates.longitude()))
                .thenReturn(Optional.empty());

        final var currentTemperature = currentTemperatureQueryService.getCurrentTemperature(
                coordinates.latitude(), coordinates.longitude());

        assertTrue(currentTemperature.isEmpty());
    }

}
