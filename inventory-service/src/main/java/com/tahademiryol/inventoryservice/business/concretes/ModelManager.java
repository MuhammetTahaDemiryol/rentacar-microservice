package com.tahademiryol.inventoryservice.business.concretes;

import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.inventoryservice.business.abstracts.ModelService;
import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.tahademiryol.inventoryservice.business.rules.ModelBusinessRules;
import com.tahademiryol.inventoryservice.entities.Model;
import com.tahademiryol.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private final ModelRepository repository;
    private final ModelMapperService mapper;
    private final ModelBusinessRules rules;

    @Override
    public List<GetAllModelsResponse> getAll() {
        var Models = repository.findAll();
        return Models
                .stream()
                .map(Model -> mapper.forResponse().map(Model, GetAllModelsResponse.class))
                .toList();
    }

    @Override
    public GetModelResponse getById(UUID id) {
        rules.checkIfModelExists(id);
        var Model = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(Model, GetModelResponse.class);
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        var Model = mapper.forRequest().map(request, Model.class);
        Model.setId(UUID.randomUUID());
        repository.save(Model);
        return mapper.forResponse().map(Model, CreateModelResponse.class);
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request) {
        rules.checkIfModelExists(id);
        var Model = mapper.forRequest().map(request, Model.class);
        Model.setId(id);
        repository.save(Model);
        return mapper.forResponse().map(Model, UpdateModelResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfModelExists(id);
        repository.deleteById(id);

    }
}
