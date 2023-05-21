package com.tahademiryol.maintenanceservice.business.abstracts;

import com.tahademiryol.maintenanceservice.business.dto.requests.create.CreateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.requests.update.UpdateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.responses.create.CreateMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetAllMaintenancesResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.update.UpdateMaintenanceResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {
    List<GetAllMaintenancesResponse> getAll();

    GetMaintenanceResponse getById(UUID id);

    CreateMaintenanceResponse add(CreateMaintenanceRequest request);

    UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request);

    UpdateMaintenanceResponse complete(UUID id);

    void delete(UUID id);
}
