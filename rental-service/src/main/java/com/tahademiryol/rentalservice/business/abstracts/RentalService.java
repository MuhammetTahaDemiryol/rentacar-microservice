package com.tahademiryol.rentalservice.business.abstracts;

import com.tahademiryol.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.update.UpdateRentalResponse;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    List<GetAllRentalsResponse> getAll();

    GetRentalResponse getById(UUID id);

    CreateRentalResponse add(CreateRentalRequest request);

    UpdateRentalResponse update(UUID id, UpdateRentalRequest request);

    void delete(UUID id);
}
