package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Price;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;

@Data
public class CreateProductCommand
{
    private final ProductId productId;
    private final String productName;
    private final float productionPerHectare;
    private final Price initialPrice;
}
