package com.tahademiryol.inventoryservice.business.concretes;

import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.inventoryservice.business.abstracts.CarService;
import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateCarResponse;
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
        repository.save(car);
        return mapper.forResponse().map(car, CreateCarResponse.class);
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

    }
}
