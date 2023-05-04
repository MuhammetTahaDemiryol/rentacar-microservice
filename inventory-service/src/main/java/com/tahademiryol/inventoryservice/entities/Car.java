package com.tahademiryol.inventoryservice.entities;

import com.tahademiryol.inventoryservice.entities.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private  int modelYear;
    private String plate;
    @Enumerated(EnumType.STRING)
    private State state;
    private double dailyPrice;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;


}
