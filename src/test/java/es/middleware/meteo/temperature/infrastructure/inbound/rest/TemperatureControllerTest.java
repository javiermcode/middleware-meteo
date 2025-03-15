package es.middleware.meteo.temperature.infrastructure.inbound.rest;

import es.middleware.meteo.temperature.application.port.output.TemperatureRepository;
import es.middleware.meteo.temperature.dataset.CurrentTemperatureServiceTestDataset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class TemperatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Test
    void testGetCurrentTemperatureWhenInCache() throws Exception {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();

        temperatureRepository.save(coordinates.latitude(), coordinates.longitude(), mockedCurrentTemperature);

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature/current"
                +"?latitude=" + coordinates.latitude()
                + "&longitude=" + coordinates.longitude()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(Assertions::assertNotNull)
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(mockedCurrentTemperature.getLatitude()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(mockedCurrentTemperature.getLongitude()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(mockedCurrentTemperature.getTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperatureUnit").value(mockedCurrentTemperature.getTemperatureUnit()));

        temperatureRepository.deleteById(coordinates.latitude(), coordinates.longitude());
    }

    @Test
    void testGetCurrentTemperatureWhenNotInCache() throws Exception {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();

        temperatureRepository.deleteById(coordinates.latitude(), coordinates.longitude());

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature/current"
                        + "?latitude=" + coordinates.latitude()
                        + "&longitude=" + coordinates.longitude()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(Assertions::assertNotNull);

        temperatureRepository.deleteById(coordinates.latitude(), coordinates.longitude());
    }

    @Test
    void testDeleteCurrentTemperatureWhenInCache() throws Exception {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();
        final var mockedCurrentTemperature = CurrentTemperatureServiceTestDataset.mockedCurrentTemperature.get();
        temperatureRepository.save(coordinates.latitude(), coordinates.longitude(), mockedCurrentTemperature);

        mockMvc.perform(MockMvcRequestBuilders.delete("/temperature/current"
                +"?latitude=" + coordinates.latitude()
                + "&longitude=" + coordinates.longitude()))
                .andExpect(status().isOk());

    }

    @Test
    void testDeleteCurrentTemperatureWhenNotInCache() throws Exception {

        final var coordinates = CurrentTemperatureServiceTestDataset.coordinates.get();

        temperatureRepository.deleteById(coordinates.latitude(), coordinates.longitude());

        mockMvc.perform(MockMvcRequestBuilders.delete("/temperature/current"
                        +"?latitude=" + coordinates.latitude()
                        + "&longitude=" + coordinates.longitude()))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetCurrentTemperatureWithInvalidCoordinates() throws Exception {

        final var invalidCoordinates = CurrentTemperatureServiceTestDataset.invalidCoordinates.get();

        mockMvc.perform(MockMvcRequestBuilders.delete("/temperature/current"
                        +"?latitude=" + invalidCoordinates.latitude()
                        + "&longitude=" + invalidCoordinates.longitude()))
                .andExpect(status().isBadRequest());

    }

}
