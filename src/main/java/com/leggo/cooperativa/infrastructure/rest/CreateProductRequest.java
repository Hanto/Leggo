package com.leggo.cooperativa.infrastructure.rest;

import lombok.Data;

@Data
public class CreateProductRequest
{
    private final String productId;
    private final String productName;
    private final float productionPerHectare;
    private final String initialPrice;
}
