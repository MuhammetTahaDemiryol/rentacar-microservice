package com.tahademiryol.rentalservice.business.rules;

import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.rentalservice.api.clients.CarClient;
import com.tahademiryol.rentalservice.api.clients.PaymentClient;
import com.tahademiryol.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    private final CarClient carClient;
    private final PaymentClient paymentClient;

    public void checkIfRentalExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("RENTAL_NOT_EXISTS");
        }
    }

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarIsAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }

    public void ensurePaymentIsAvailable(CreateRentalPaymentRequest request) {
        var response = paymentClient.checkIfPaymentAvailable(request);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
}
