package com.leggo.cooperativa.domain.model.product;

public enum ProductType
{
    PERISHABLE
        {
            @Override
            public Product createProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, PricePerKilogram initialPricePerKilogram)
            {
                MarketRate marketRate = new MarketRate(initialPricePerKilogram);
                return new PerishableProduct(productId, name, kilogramsPerHectare, marketRate);
            }
        },
    NOT_PERISHABLE
        {
            @Override
            public Product createProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, PricePerKilogram initialPricePerKilogram)
            {
                MarketRate marketRate = new MarketRate(initialPricePerKilogram);
                return new NonPerishableProduct(productId, name, kilogramsPerHectare, marketRate);
            }
        };

    public abstract Product createProduct(ProductId productId, String name, KilogramsPerHectare kilogramsPerHectare, PricePerKilogram initialPricePerKilogram);
}
