package com.tahademiryol.inventoryservice.business.dto.requests.update;

import com.tahademiryol.commonpackage.utils.annotations.NotFutureYear;
import com.tahademiryol.commonpackage.utils.constants.Regex;
import com.tahademiryol.inventoryservice.entities.enums.State;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
    @NotNull
    private UUID modelId;
    @Min(value = 2000)
    @NotFutureYear
    private int modelYear;
    @NotBlank
    @Pattern(regexp = Regex.Plate)
    private String plate;
    @NotNull
    private State state;
    @Min(value = 1)
    private double dailyPrice;

}