package com.tahademiryol.rentalservice.repository;

import com.tahademiryol.rentalservice.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
}
