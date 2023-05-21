package com.tahademiryol.rentalservice.api.clients;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @Retry(name = "retry-payment")
    @PostMapping(value = "/api/payments/check-payment-process")
    ClientResponse checkIfPaymentAvailable(@RequestBody CreateRentalPaymentRequest request);
}
