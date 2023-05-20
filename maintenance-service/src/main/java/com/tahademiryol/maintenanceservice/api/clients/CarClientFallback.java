package com.tahademiryol.maintenanceservice.api.clients;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {

    @Override
    public ClientResponse checkIfCarIsAvailable(UUID carId) {
        log.info("INVENTORY IS DOWN-MAINTENANCE!");
        throw new RuntimeException("INVENTORY-SERVICE IS NOT AVAILABLE RIGHT NOW-MAINTENANCE!");
    }


}
