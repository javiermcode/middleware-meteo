package es.middleware.meteo.temperature.infrastructure.outbound.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.middleware.meteo.temperature.domain.model.Temperature;
import es.middleware.meteo.temperature.infrastructure.outbound.event.kafka.mapper.EventTemperatureDtoMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class KafkaAspect {

    private static final Logger logger = LoggerFactory.getLogger(KafkaAspect.class);

    private final KafkaProducer kafkaProducer;

    private final ObjectMapper objectMapper;

    public KafkaAspect(KafkaProducer kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(public * es.middleware.meteo.temperature.application.service.CurrentTemperatureQueryService.getCurrentTemperature(..))")
    private void currentTemperatureRequest() {
    }

    @AfterReturning(value = "currentTemperatureRequest()", returning = "result")
    public void sendKafkaMessageAfter(JoinPoint joinPoint, Object result) {

        try {
            Optional<Temperature> optionalResult = (Optional<Temperature>)result;
            if(optionalResult.isPresent()) {
                kafkaProducer.sendMessage(objectMapper.writeValueAsString(EventTemperatureDtoMapper.mapToDto(optionalResult.get())));
            } else {
                logger.warn("Attempt to send empty Kafka message: {}", result);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error converting object to json for Kafka message");
        }

    }
}
