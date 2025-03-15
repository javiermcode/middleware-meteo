package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
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
class CurrentTemperatureSaveServiceTest {


    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private CurrentTemperatureSaveService currentTemperatureSaveService;

    @Test
    void testDeleteCurrentTemperatureFromCache() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        when(temperatureRepository.save(coordinates.latitude(), coordinates.longitude(), mockedCurrentTemperature))
                .thenReturn(Optional.ofNullable(mockedCurrentTemperature));

        final var savedTemperature = currentTemperatureSaveService.saveCurrentTemperature(coordinates.latitude(),
                coordinates.longitude(), mockedCurrentTemperature);
        assertTrue(savedTemperature.isPresent());
        assertEquals(mockedCurrentTemperature.getLatitude(), savedTemperature.get().getLatitude());
        assertEquals(mockedCurrentTemperature.getLongitude(), savedTemperature.get().getLongitude());
        assertEquals(mockedCurrentTemperature.getTemperature(), savedTemperature.get().getTemperature());
        assertEquals(mockedCurrentTemperature.getTemperatureUnit(), savedTemperature.get().getTemperatureUnit());
        assertEquals(mockedCurrentTemperature.getCreatedAt(), savedTemperature.get().getCreatedAt());

    }

}
