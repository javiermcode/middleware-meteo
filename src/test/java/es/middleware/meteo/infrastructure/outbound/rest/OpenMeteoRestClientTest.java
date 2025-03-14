package es.middleware.meteo.infrastructure.outbound.rest;

import es.middleware.meteo.temperature.infrastructure.outbound.rest.OpenMeteoRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class OpenMeteoRestClientTest {

    @Autowired
    OpenMeteoRestClient openMeteoRestClient;

    @Test
    public void getCurrentTemperature() {

        double latitude = 90;
        double longitude = -180;

        final var currentTemperature = openMeteoRestClient.getCurrentTemperature(latitude, longitude);

        assertTrue(currentTemperature.isPresent());
    }
}
