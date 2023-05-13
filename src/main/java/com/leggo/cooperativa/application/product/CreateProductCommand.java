package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Price;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.rest.CreateProductRequest;
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
    private final Price initialPrice;

    public CreateProductCommand(CreateProductRequest request)
    {
        this.productId = new ProductId(request.getProductId());
        this.productName = request.getProductName();
        this.productionPerHectare = request.getProductionPerHectare();
        this.initialPrice = new Price(new BigDecimal(request.getInitialPrice()));
    }
}
