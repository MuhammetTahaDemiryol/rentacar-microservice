package com.tahademiryol.inventoryservice.business.rules;

import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandBusinessRules {
    private final BrandRepository repository;

    public void checkIfBrandExists(UUID id) {
        if (!repository.existsById(id)) {

            throw new BusinessException("Brand_NOT_EXISTS");
        }
    }
}
