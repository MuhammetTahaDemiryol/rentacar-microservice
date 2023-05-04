package com.tahademiryol.inventoryservice.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetModelResponse {
    private UUID id;
    private UUID brandId;
    private String name;
}
