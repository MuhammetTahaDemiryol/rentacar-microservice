package com.tahademiryol.rentalservice.business.kafka.producer;

import com.tahademiryol.commonpackage.events.rental.RentalCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void sendMessage(RentalCreatedEvent event) {
        log.info(String.format("rental-created-event sent -> %s", event.toString()));
        Message<RentalCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "rental-created")
                .build();

        kafkaTemplate.send(message);
    }
}
