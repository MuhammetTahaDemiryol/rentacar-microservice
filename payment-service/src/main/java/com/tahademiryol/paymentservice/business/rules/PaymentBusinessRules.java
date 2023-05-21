package com.tahademiryol.paymentservice.business.rules;

import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Payment does not exist");
        }
    }

    public void checkIfBalanceIfEnough(double balance, double price) {
        if (balance < price) {
            throw new BusinessException("Balance is not enough");
        }
    }

    public void checkIfCardExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new BusinessException("Card already exists");
        }
    }

    public void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new BusinessException("Payment is not valid");
        }
    }
}
