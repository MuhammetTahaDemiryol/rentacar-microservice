package com.tahademiryol.inventoryservice.business.concretes;

import com.tahademiryol.commonpackage.events.CarCreatedEvent;
import com.tahademiryol.commonpackage.events.CarDeletedEvent;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.inventoryservice.business.abstracts.CarService;
import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.tahademiryol.inventoryservice.business.kafka.producer.InventoryProducer;
import com.tahademiryol.inventoryservice.business.rules.CarBusinessRules;
import com.tahademiryol.inventoryservice.entities.Car;
import com.tahademiryol.inventoryservice.entities.enums.State;
import com.tahademiryol.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private final CarRepository repository;
    private final ModelMapperService mapper;
    private final CarBusinessRules rules;
    private final InventoryProducer producer;

    @Override
    public List<GetAllCarsResponse> getAll() {
        var cars = repository.findAll();
        return cars
                .stream()
                .map(car -> mapper.forResponse().map(car, GetAllCarsResponse.class))
                .toList();
    }

    @Override
    public GetCarResponse getById(UUID id) {
        rules.checkIfCarExists(id);
        var car = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(car, GetCarResponse.class);
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID());
        car.setState(State.Available);
        var createdCar = repository.save(car);
        sendKafkaCarCreatedEvent(createdCar);
        return mapper.forResponse().map(createdCar, CreateCarResponse.class);
    }

    @Override
    public UpdateCarResponse update(UUID id, UpdateCarRequest request) {
        rules.checkIfCarExists(id);
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(id);
        repository.save(car);
        return mapper.forResponse().map(car, UpdateCarResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfCarExists(id);
        repository.deleteById(id);
        sendKafkaCarDeletedEvent(id);

    }

    // send data to filter-service via kafka and filter-service will record it to mongoDB
    public void sendKafkaCarCreatedEvent(Car createdCar) {
        var event = mapper.forResponse().map(createdCar, CarCreatedEvent.class);
        producer.sendMessage(event);
    }

    private void sendKafkaCarDeletedEvent(UUID id) {
        producer.sendMessage(new CarDeletedEvent(id));
    }

}
