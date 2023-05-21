package com.tahademiryol.maintenanceservice.business.concretes;

import com.tahademiryol.commonpackage.events.maintenance.MaintenanceCompletedEvent;
import com.tahademiryol.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.tahademiryol.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.tahademiryol.commonpackage.kafka.producer.KafkaProducer;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.maintenanceservice.api.clients.CarClient;
import com.tahademiryol.maintenanceservice.business.abstracts.MaintenanceService;
import com.tahademiryol.maintenanceservice.business.dto.requests.create.CreateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.requests.update.UpdateMaintenanceRequest;
import com.tahademiryol.maintenanceservice.business.dto.responses.create.CreateMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetAllMaintenancesResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.get.GetMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.dto.responses.update.UpdateMaintenanceResponse;
import com.tahademiryol.maintenanceservice.business.rules.MaintenanceBusinessRules;
import com.tahademiryol.maintenanceservice.entities.Maintenance;
import com.tahademiryol.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final MaintenanceBusinessRules rules;
    private final CarClient carClient;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        var maintenances = repository.findAll();
        return maintenances
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
                .toList();
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExists(id);
        var maintenance = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());
        var maintenance = mapper.forRequest().map(request, Maintenance.class);

        maintenance.setId(UUID.randomUUID());
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());

        var createdMaintenance = repository.save(maintenance);
        sendKafkaMaintenanceCreatedEvent(createdMaintenance.getCarId());
        return mapper.forResponse().map(createdMaintenance, CreateMaintenanceResponse.class);
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);
        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);

        if (maintenance.isCompleted()) {
            maintenance.setEndDate(LocalDateTime.now());
            sendKafkaMaintenanceCompletedEvent(maintenance.getCarId());
        }

        repository.save(maintenance);
        return mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);
    }

    @Override
    public UpdateMaintenanceResponse complete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        rules.checkIfMaintenanceAvailable(id);
        var maintenance = repository.findById(id).orElseThrow();
        maintenance.setEndDate(LocalDateTime.now());
        sendKafkaMaintenanceCompletedEvent(maintenance.getCarId());
        return mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        sendKafkaMaintenanceDeletedEvent(id);
        repository.deleteById(id);
    }

    public void sendKafkaMaintenanceCreatedEvent(UUID carID) {
        var event = new MaintenanceCreatedEvent(carID);
        producer.sendMessage(event, "maintenance-created");
    }

    private void sendKafkaMaintenanceDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new MaintenanceDeletedEvent(carId), "maintenance-deleted");
    }

    private void sendKafkaMaintenanceCompletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCompletedEvent(carId), "maintenance-completed");
    }

}
