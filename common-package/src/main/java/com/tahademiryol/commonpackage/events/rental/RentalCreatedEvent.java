package com.tahademiryol.commonpackage.events.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalCreatedEvent implements Event {
    private UUID carId;
}
