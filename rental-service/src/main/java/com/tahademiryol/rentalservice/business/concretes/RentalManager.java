package com.tahademiryol.rentalservice.business.concretes;

import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.rentalservice.business.abstracts.RentalService;
import com.tahademiryol.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.tahademiryol.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.tahademiryol.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.tahademiryol.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import com.tahademiryol.rentalservice.business.rules.RentalBusinessRules;
import com.tahademiryol.rentalservice.entities.Rental;
import com.tahademiryol.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var Rentals = repository.findAll();
        return Rentals
                .stream()
                .map(Rental -> mapper.forResponse().map(Rental, GetAllRentalsResponse.class))
                .toList();
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);
        var Rental = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(Rental, GetRentalResponse.class);
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(UUID.randomUUID());
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());
        repository.save(rental);
        return mapper.forResponse().map(rental, CreateRentalResponse.class);
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);
        var Rental = mapper.forRequest().map(request, Rental.class);
        Rental.setId(id);
        repository.save(Rental);
        return mapper.forResponse().map(Rental, UpdateRentalResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        repository.deleteById(id);

    }

    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }
}