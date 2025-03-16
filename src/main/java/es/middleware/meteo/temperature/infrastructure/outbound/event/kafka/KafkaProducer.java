package es.middleware.meteo.temperature.infrastructure.outbound.event.kafka;

import es.middleware.meteo.temperature.domain.model.configuration.ConfigurationProperties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConfigurationProperties configurationProperties;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ConfigurationProperties configurationProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.configurationProperties = configurationProperties;
    }

    public void sendMessage(String message) {

        final ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(configurationProperties.temperatureKafkaTopicName(), message);

        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(producerRecord);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                handleSuccess(producerRecord.topic(), message);
            } else {
                handleFailure(message, producerRecord, ex);
            }
        });
    }

    private void handleSuccess(String topic, String message) {
        logger.info("Kafka message successfully sent. Topic {} Message {}", topic, message);
    }

    private void handleFailure(String message, ProducerRecord<String, String> producerRecord, Throwable e) {
        logger.error("Kafka message not sent. Topic {} Message {}", producerRecord.topic(), message, e);
    }

}
