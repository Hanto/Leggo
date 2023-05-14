package com.leggo.cooperativa.infrastructure.rest.requests;

import lombok.Data;

@Data
public class CreateProductRequest
{
    private final String productId;
    private final String productName;
    private final float productionPerHectare;
    private final String initialPricePerKilogram;
    private final String productType;
}
