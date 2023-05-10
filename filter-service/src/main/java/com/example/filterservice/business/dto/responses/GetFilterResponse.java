package com.example.filterservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFilterResponse {
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

