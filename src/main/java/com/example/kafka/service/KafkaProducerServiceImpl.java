package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final String TEMPL = "{\"id\":\"%1$s\", \"message\": \"%2$s\"}";

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.producer.topic}")
    private String topic;

    @Override
    public void send() {
        logger.info("message sent: {}", getMessage());
        kafkaTemplate.send(topic, getMessage());
    }

    private String getMessage() {
        return String.format(TEMPL, generateMessage(), generateID());
    }

    private String generateMessage() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 104;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    private UUID generateID() {

        UUID id = UUID.randomUUID();
        return id;
    }
}
