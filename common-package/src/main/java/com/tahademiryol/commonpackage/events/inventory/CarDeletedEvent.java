package com.tahademiryol.commonpackage.events.inventory;

import com.tahademiryol.commonpackage.events.rental.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDeletedEvent implements Event {
    private UUID carId;
}
