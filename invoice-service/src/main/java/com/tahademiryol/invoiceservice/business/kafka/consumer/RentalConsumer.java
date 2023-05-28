package com.tahademiryol.invoiceservice.business.kafka.consumer;

import com.tahademiryol.commonpackage.events.invoice.InvoiceCreatedEvent;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.invoiceservice.business.abstracts.InvoiceService;
import com.tahademiryol.invoiceservice.entities.Invoice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RentalConsumer {
    private final InvoiceService service;
    private final ModelMapperService mapper;

    @KafkaListener(topics = "invoice-created", groupId = "invoice-rental-created")
    public void consume(InvoiceCreatedEvent event) {
        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        service.add(invoice);
        log.info("Invoice created event consumed {}", event);
    }
}