package com.example.filterservice.business.kafka.consumer;


import com.example.filterservice.business.abstracts.FilterService;
import com.tahademiryol.commonpackage.events.rental.RentalCreatedEvent;
import com.tahademiryol.commonpackage.events.rental.RentalDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentalConsumer {
    private final FilterService service;


    @KafkaListener(topics = "rental-created", groupId = "filter-rental-created")
    public void consume(RentalCreatedEvent event) {
        var filter = service.getByCarId(event.getCarId());
        filter.setState("Rented");
        service.add(filter);
        log.info("Rental created event consumed {}", event);
    }

    @KafkaListener(topics = "rental-deleted", groupId = "filter-rental-deleted")
    public void consume(RentalDeletedEvent event) {
        var filter = service.getByCarId(event.getCarId());
        filter.setState("Available");
        service.add(filter);
        log.info("Rental deleted event consumed {}", event);
    }
    
}
