package com.leggo.cooperativa.infrastructure.rest.requests;

import com.leggo.cooperativa.application.product.CreateProductCommand;
import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CreateProductRequest
{
    @NotNull private final String productId;
    @NotNull private final String productName;
    @NotNull private final Double productionPerHectare;
    @NotNull private final String initialPricePerKilogram;
    @NotNull private final String productType;

    public CreateProductCommand toCommand()
    {
        return CreateProductCommand.builder()
            .productId(ProductId.of(productId))
            .productName(productName)
            .kilogramsPerHectare(KilogramsPerHectare.of(productionPerHectare))
            .initialPricePerKilogram(PricePerKilogram.of(initialPricePerKilogram))
            .productType(productTypeFromString(productType))
            .build();
    }

    private ProductType productTypeFromString(String string)
    {
        if (string == null)
            throw new IllegalArgumentException("productType cannot be null");

        return ProductType.valueOf(string);
    }
}
