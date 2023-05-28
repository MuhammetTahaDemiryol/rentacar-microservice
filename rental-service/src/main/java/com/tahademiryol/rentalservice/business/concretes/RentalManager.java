package com.tahademiryol.rentalservice.business.concretes;

import com.tahademiryol.commonpackage.events.invoice.InvoiceCreatedEvent;
import com.tahademiryol.commonpackage.events.rental.RentalCreatedEvent;
import com.tahademiryol.commonpackage.events.rental.RentalDeletedEvent;
import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.tahademiryol.commonpackage.utils.dto.GetCarResponse;
import com.tahademiryol.commonpackage.utils.kafka.producer.KafkaProducer;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.rentalservice.api.clients.CarClient;
import com.tahademiryol.rentalservice.business.abstracts.RentalService;
import com.tahademiryol.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import com.tahademiryol.rentalservice.business.rules.RentalBusinessRules;
import com.tahademiryol.rentalservice.entities.Rental;
import com.tahademiryol.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;
    private final KafkaProducer producer;
    private final CarClient carClient;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var Rentals = repository.findAll();
        return Rentals
                .stream()
                .map(Rental -> mapper.forResponse().map(Rental, GetAllRentalsResponse.class))
                .toList();
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);
        var Rental = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(Rental, GetRentalResponse.class);
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());
        InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(UUID.randomUUID());
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());

        mergeRequest(request, invoiceCreatedEvent, rental);

        // !TODO: Payment method create
        CreateRentalPaymentRequest rentalPaymentRequest = new CreateRentalPaymentRequest();
        mapper.forRequest().map(request.getPaymentRequest(), rentalPaymentRequest);
        rentalPaymentRequest.setPrice(rental.getTotalPrice());

        rules.ensurePaymentIsAvailable(rentalPaymentRequest);

        repository.save(rental);
        sendKafkaRentalCreatedEvent(request.getCarId());
        sendKafkaInvoiceCreatedEvent(invoiceCreatedEvent);
        return mapper.forResponse().map(rental, CreateRentalResponse.class);
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);
        var Rental = mapper.forRequest().map(request, Rental.class);
        Rental.setId(id);
        repository.save(Rental);
        return mapper.forResponse().map(Rental, UpdateRentalResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        sendKafkaRentalDeletedEvent(id);
        repository.deleteById(id);

    }

    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }

    private void sendKafkaRentalCreatedEvent(UUID id) {
        producer.sendMessage(new RentalCreatedEvent(id), "rental-created");
    }

    private void sendKafkaRentalDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new RentalDeletedEvent(carId), "rental-deleted");
    }

    private void sendKafkaInvoiceCreatedEvent(InvoiceCreatedEvent event) {
        producer.sendMessage(event, "invoice-created");
    }

    private void mergeRequest(CreateRentalRequest request, InvoiceCreatedEvent event, Rental rental) {
        GetCarResponse response = carClient.getById(request.getCarId());
        event.setBrandName(response.getBrandName());
        event.setPlate(response.getPlate());
        event.setModelName(response.getModelName());
        event.setRentedAt(rental.getRentedAt());
        event.setTotalPrice(rental.getTotalPrice());
        event.setDailyPrice(rental.getDailyPrice());
        event.setCardHolder(request.getPaymentRequest().getCardHolder());
        event.setRentedForDays(rental.getRentedForDays());
    }
}