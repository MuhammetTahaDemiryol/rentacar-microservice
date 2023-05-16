package com.tahademiryol.rentalservice.business.rules;

import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;

    public void checkIfRentalExists(UUID id) {
        if (!repository.existsById(id)) {
            //TODO: BusinessException
            throw new BusinessException("CAR_NOT_EXISTS");
        }
    }
}
