package com.tahademiryol.commonpackage.utils.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalPaymentRequest extends PaymentRequest {
    @Min(value = 1, message = "Balance must be at least 1")
    private double price;
}
