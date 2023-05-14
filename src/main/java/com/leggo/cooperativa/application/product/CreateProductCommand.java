package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateProductRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor @Setter @Getter
public class CreateProductCommand
{
    private final ProductId productId;
    private final String productName;
    private final float productionPerHectare;
    private final PricePerKilogram initialPricePerKilogram;
    private final ProductType productType;

    public CreateProductCommand(CreateProductRequest request)
    {
        this.productId = new ProductId(request.getProductId());
        this.productName = request.getProductName();
        this.productionPerHectare = request.getProductionPerHectare();
        this.initialPricePerKilogram = new PricePerKilogram(new BigDecimal(request.getInitialPricePerKilogram()));
        this.productType = productTypeFrom(request.getProductType());
    }

    private ProductType productTypeFrom(String string)
    {
        if (string == null)
            throw new IllegalArgumentException("productType cannot be null");

        return ProductType.valueOf(string);
    }
}
