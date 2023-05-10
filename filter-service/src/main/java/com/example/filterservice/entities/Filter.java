package com.example.filterservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    @Id
    private String id;
    private UUID carId;
    private UUID modelId;
    private UUID brandId;

    private String brandName;
    private String modelName;
    private String plate;

    private int modelYear;
    private double dailyPrice;
    private String state;

}
