package com.tahademiryol.rentalservice.api.clients;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {

    @Override
    @Retry(name = "retry-rental", fallbackMethod = "checkIfCarIsAvailable") // TODO! fallback check
    public ClientResponse checkIfCarIsAvailable(UUID carId) {
        log.info("INVENTORY IS DOWN!");
        throw new RuntimeException("INVENTORY-SERVICE IS NOT AVAILABLE RIGHT NOW!");
    }
}
