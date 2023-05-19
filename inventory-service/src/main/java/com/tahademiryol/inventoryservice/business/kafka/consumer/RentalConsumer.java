package com.tahademiryol.inventoryservice.business.kafka.consumer;


import com.tahademiryol.commonpackage.events.rental.RentalCreatedEvent;
import com.tahademiryol.commonpackage.events.rental.RentalDeletedEvent;
import com.tahademiryol.inventoryservice.business.abstracts.CarService;
import com.tahademiryol.inventoryservice.entities.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentalConsumer {
    private final CarService service;


    @KafkaListener(topics = "rental-created", groupId = "inventory-rental-created")
    public void consume(RentalCreatedEvent event) {
        service.changeStateByCarId(State.Rented, event.getCarId());
        log.info("Rental created event consumed {}", event);
    }

    @KafkaListener(topics = "rental-deleted", groupId = "inventory-rental-deleted")
    public void consume(RentalDeletedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Rental deleted event consumed {}", event);
    }


}
