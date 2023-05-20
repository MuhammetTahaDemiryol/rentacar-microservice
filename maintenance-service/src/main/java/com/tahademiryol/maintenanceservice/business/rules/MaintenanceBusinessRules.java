package com.tahademiryol.maintenanceservice.business.rules;

import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.maintenanceservice.api.clients.CarClient;
import com.tahademiryol.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarClient carClient;

    public void checkIfMaintenanceExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("MAINTENANCE_NOT_EXISTS");
        }
    }

    public void checkIfMaintenanceAvailable(UUID id) {
        if (repository.findById(id).orElseThrow().isCompleted()) {
            throw new BusinessException("MAINTENANCE_ENDED_ALREADY");
        }
    }

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarIsAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }

}
