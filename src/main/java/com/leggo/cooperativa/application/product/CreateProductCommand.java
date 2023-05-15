package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor @Setter @Getter @Builder
public class CreateProductCommand
{
    private final ProductId productId;
    private final String productName;
    private final KilogramsPerHectare kilogramsPerHectare;
    private final PricePerKilogram initialPricePerKilogram;
    private final ProductType productType;
}
