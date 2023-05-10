package com.tahademiryol.inventoryservice.business.kafka.producer;

import com.tahademiryol.commonpackage.events.BrandDeletedEvent;
import com.tahademiryol.commonpackage.events.CarCreatedEvent;
import com.tahademiryol.commonpackage.events.CarDeletedEvent;
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
public class InventoryProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void sendMessage(CarCreatedEvent event) {
        log.info(String.format("car-created-event sent -> %s", event.toString()));
        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-created")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarDeletedEvent event) {
        log.info(String.format("car-deleted-event sent -> %s", event.toString()));
        Message<CarDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-deleted")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendBrandDeleteMessage(BrandDeletedEvent event) {
        log.info(String.format("brand-deleted-event sent -> %s", event));
        Message<BrandDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "brand-deleted")
                .build();

        kafkaTemplate.send(message);
    }


}
