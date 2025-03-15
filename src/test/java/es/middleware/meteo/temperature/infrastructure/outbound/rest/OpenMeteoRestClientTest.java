package es.middleware.meteo.temperature.infrastructure.outbound.rest;

import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class OpenMeteoRestClientTest {

    @Autowired
    private OpenMeteoRestClient openMeteoRestClient;

    @Test
    void testGetCurrentTemperatureFromProviderWithValidCoordinates() {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();

        final var currentTemperature = openMeteoRestClient.getCurrentTemperature(coordinates.latitude(), coordinates.longitude());

        assertTrue(currentTemperature.isPresent());
    }

    @Test
    void testGetCurrentTemperatureFromProviderWithInvalidCoordinates() {

        final var invalidCoordinates = CurrentTemperatureServiceTestDataset.invalidCoordinates.get();

        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> openMeteoRestClient.getCurrentTemperature(
                        invalidCoordinates.latitude(),
                        invalidCoordinates.longitude()));
    }

}
