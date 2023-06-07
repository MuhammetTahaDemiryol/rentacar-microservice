package com.tahademiryol.inventoryservice.api.controllers;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import com.tahademiryol.commonpackage.utils.dto.GetCarResponse;
import com.tahademiryol.inventoryservice.business.abstracts.CarService;
import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {
    private final CarService service;

    @GetMapping
    // Secured, PreAuthorize, and PostAuthorize are all Spring Security annotations.
    // @Secured("ROLE_admin")
    //@PreAuthorize("hasRole('user') or hasRole('admin')") //SPel
    //@PreAuthorize(Roles.AdminAndUser)
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    //@PostAuthorize("hasRole('admin') || returnObject.getModelYear() == 2019")
    public GetCarResponse getById(@PathVariable UUID id) { //@AuthenticationPrincipal Jwt jwt)
//        System.out.println(jwt.getClaims().get("email"));
//        System.out.println(jwt.getClaims().get("given_name"));
//        System.out.println(jwt.getClaims().get("family_name"));
//        System.out.println(jwt.getClaims().get("sub"));
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-if-car-is-available/{id}")
    public ClientResponse checkIfCarIsAvailable(@PathVariable UUID id) {
        return service.checkIfCarIsAvailable(id);
    }
}
