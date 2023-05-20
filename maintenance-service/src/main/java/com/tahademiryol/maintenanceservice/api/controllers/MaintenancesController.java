package com.tahademiryol.maintenanceservice.api.controllers;

import com.tahademiryol.maintenanceservice.business.abstracts.MaintenanceService;
import com.tahademiryol.maintenanceservice.business.dto.requests.create.CreateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.requests.update.UpdateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.responses.create.CreateMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetAllMaintenancesResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.update.UpdateMaintenanceResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenances")
public class MaintenancesController {
    private final MaintenanceService service;

    @GetMapping
    public List<GetAllMaintenancesResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetMaintenanceResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMaintenanceResponse add(@Valid @RequestBody CreateMaintenanceRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateMaintenanceResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateMaintenanceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-if-Maintenance-is-available/{id}")
    public GetMaintenanceResponse complete(@PathVariable UUID id) {
        return service.complete(id);
    }
}
