package com.tahademiryol.commonpackage.events.maintenance;

import com.tahademiryol.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCompletedEvent implements Event {
    private UUID carId;
}
