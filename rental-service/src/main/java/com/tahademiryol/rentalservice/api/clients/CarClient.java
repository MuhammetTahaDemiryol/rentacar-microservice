package com.tahademiryol.rentalservice.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "inventory-service")
public interface CarClient {
    @GetMapping("/api/cars/check-if-car-is-available/{carId}")
    void checkIfCarIsAvailable(@PathVariable UUID carId);
}
