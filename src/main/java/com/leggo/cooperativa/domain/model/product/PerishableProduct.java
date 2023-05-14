package com.leggo.cooperativa.domain.model.product;

public class PerishableProduct extends Product
{
    public PerishableProduct(ProductId productId, String name, float productionPerHectare, MarketRate marketRates)
    {
        super(productId, name, productionPerHectare, marketRates);
    }
}
