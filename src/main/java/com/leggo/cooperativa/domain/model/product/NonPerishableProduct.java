package com.leggo.cooperativa.domain.model.product;

public class NonPerishableProduct extends Product
{
    public NonPerishableProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, MarketRate marketRates)
    {
        super(productId, name, kilogramsPerHectare, marketRates);
    }
}
