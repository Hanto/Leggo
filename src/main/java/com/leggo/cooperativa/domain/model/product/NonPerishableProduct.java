package com.leggo.cooperativa.domain.model.product;

public class NonPerishableProduct extends Product
{
    public NonPerishableProduct(ProductId productId, String name, float productionPerHectare, MarketRate marketRates)
    {
        super(productId, name, productionPerHectare, marketRates);
    }
}
