package com.leggo.cooperativa.domain.model.product;

public class PerishableProduct extends Product
{
    public PerishableProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, MarketRate marketRates)
    {
        super(productId, name, kilogramsPerHectare, marketRates);
    }
}
