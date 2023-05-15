package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;

public class PerishableProduct extends Product
{
    public PerishableProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, MarketRate marketRates)
    {
        super(productId, name, kilogramsPerHectare, marketRates);
    }
}
