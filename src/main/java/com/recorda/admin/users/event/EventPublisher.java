package com.recorda.admin.users.event;

import com.recorda.admin.users.event.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes an event in the underlying Kafka bus
 */
@Service
public class EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(EventPublisher.class);

    @Value("${kafka.users.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Message message) {
        logger.info(String.format("#### EVENT / Sending message -> %s", message.toString()));
        kafkaTemplate.send(topic,message);

    }
}
