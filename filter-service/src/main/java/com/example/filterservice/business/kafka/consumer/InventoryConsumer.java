package com.example.filterservice.business.kafka.consumer;


import com.example.filterservice.business.abstracts.FilterService;
import com.example.filterservice.entities.Filter;
import com.tahademiryol.commonpackage.events.inventory.BrandDeletedEvent;
import com.tahademiryol.commonpackage.events.inventory.CarCreatedEvent;
import com.tahademiryol.commonpackage.events.inventory.CarDeletedEvent;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryConsumer {
    private final FilterService service;
    private final ModelMapperService mapper;

    @KafkaListener(topics = "car-created", groupId = "filter-car-created")
    public void consume(CarCreatedEvent event) {
        var filter = mapper.forRequest().map(event, Filter.class);
        service.add(filter);
        log.info("Car created event consumed {}", event);
    }

    @KafkaListener(topics = "car-deleted", groupId = "filter-car-deleted")
    public void consume(CarDeletedEvent event) {
        service.deleteByCarId(event.getCarId());
        log.info("Car deleted event consumed {}", event);
    }

    @KafkaListener(topics = "brand-deleted", groupId = "filter-brand-deleted")
    public void deleteBrand(BrandDeletedEvent event) {
        service.deleteAllByBrandId(event.getBrandId());
        log.info("Brand deleted event consumed {}", event);
    }


}
