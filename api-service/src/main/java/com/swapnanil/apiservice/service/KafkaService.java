package com.swapnanil.apiservice.service;

import java.util.Properties;

import com.swapnanil.apiservice.config.Constants;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {
    private final KafkaProducer<String, String> producer;

    public KafkaService() {
        this.producer = new KafkaProducer<>(getKafkaProps());
    }

    public void produceMessage(String message) {
        producer.send(new ProducerRecord<>(Constants.TOPIC_NAME, message));
    }

    private Properties getKafkaProps() {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", Constants.KAFKA_SERVER);
//        properties.setProperty("security.protocol", "SSL");
//        properties.setProperty("ssl.truststore.location", Constants.TRUSTSTORE_LOCATION);
//        properties.setProperty("ssl.truststore.password", Constants.TRUSTSTORE_PASSWORD);
//        properties.setProperty("ssl.keystore.type", "PKCS12");
//        properties.setProperty("ssl.keystore.location", Constants.KEYSTORE_LOCATION);
//        properties.setProperty("ssl.keystore.password", Constants.KEYSTORE_PASSWORD);
//        properties.setProperty("ssl.key.password", Constants.KEY_PASSWORD);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        return properties;
    }

    @PreDestroy
    public void preDestroy() {
        producer.close();
    }
}