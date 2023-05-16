package com.tahademiryol.inventoryservice.business.rules;

import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.inventoryservice.entities.enums.State;
import com.tahademiryol.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;

    public void checkIfCarExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("CAR_NOT_EXISTS");
        }
    }

    public void checkCarAvailability(UUID id) {
        var car = repository.findById(id).orElseThrow();
        if (!car.getState().equals(State.Available)) {
            throw new BusinessException("CAR_NOT_AVAILABLE");
        }
    }
}
