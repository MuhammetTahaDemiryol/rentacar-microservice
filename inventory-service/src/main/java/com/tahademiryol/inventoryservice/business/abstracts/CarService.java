package com.tahademiryol.inventoryservice.business.abstracts;

import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.tahademiryol.inventoryservice.entities.enums.State;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<GetAllCarsResponse> getAll();

    GetCarResponse getById(UUID id);

    CreateCarResponse add(CreateCarRequest request);

    UpdateCarResponse update(UUID id, UpdateCarRequest request);

    void delete(UUID id);

    void checkIfCarIsAvailable(UUID id);

    void changeStateByCarId(State state, UUID id);
}
