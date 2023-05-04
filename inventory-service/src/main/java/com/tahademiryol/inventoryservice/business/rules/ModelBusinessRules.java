package com.tahademiryol.inventoryservice.business.rules;

import com.tahademiryol.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelBusinessRules {
    private final ModelRepository repository;

    public void checkIfModelExists(UUID id) {
        if (!repository.existsById(id)) {
            //TODO: BusinessException
            throw new RuntimeException("Model_NOT_EXISTS");
        }
    }
}
