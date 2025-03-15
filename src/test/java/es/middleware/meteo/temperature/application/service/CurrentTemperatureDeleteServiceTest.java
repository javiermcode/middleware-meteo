package es.middleware.meteo.temperature.application.service;

import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class CurrentTemperatureDeleteServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @Spy
    @InjectMocks
    private CurrentTemperatureDeleteService currentTemperatureDeleteService;

    @Test
    void testDeleteCurrentTemperatureFromCache() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        doNothing().when(temperatureRepository).deleteById(coordinates.latitude(), coordinates.longitude());
        currentTemperatureDeleteService.deleteCurrentTemperature(coordinates.latitude(), coordinates.longitude());

        verify(currentTemperatureDeleteService, times(1))
                .deleteCurrentTemperature(coordinates.latitude(), coordinates.longitude());
    }

}
