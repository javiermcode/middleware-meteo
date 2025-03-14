package es.middleware.meteo.temperature.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BeanConfiguration {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

}
