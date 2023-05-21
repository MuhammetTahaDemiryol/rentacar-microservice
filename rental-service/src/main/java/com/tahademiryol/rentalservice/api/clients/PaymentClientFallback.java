package com.tahademiryol.rentalservice.api.clients;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentClientFallback implements PaymentClient {

    @Override
    public ClientResponse checkIfPaymentAvailable(CreateRentalPaymentRequest request) {
        log.info("Payment SERVICE IS DOWN!");
        throw new RuntimeException("Payment-SERVICE NOT AVAILABLE RIGHT NOW!");
    }
}
