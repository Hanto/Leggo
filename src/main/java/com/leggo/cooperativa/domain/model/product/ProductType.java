package com.leggo.cooperativa.domain.model.product;

public enum ProductType
{
    PERISHABLE
        {
            @Override
            public Product createProduct(ProductId productId, String name, float productionPerHectare, PricePerKilogram initialPricePerKilogram)
            {
                MarketRate marketRate = new MarketRate(initialPricePerKilogram);
                return new PerishableProduct(productId, name, productionPerHectare, marketRate);
            }
        },
    NOT_PERISHABLE
        {
            @Override
            public Product createProduct(ProductId productId, String name, float productionPerHectare, PricePerKilogram initialPricePerKilogram)
            {
                MarketRate marketRate = new MarketRate(initialPricePerKilogram);
                return new NonPerishableProduct(productId, name, productionPerHectare, marketRate);
            }
        };

    public abstract Product createProduct(ProductId productId, String name, float productionPerHectare, PricePerKilogram initialPricePerKilogram);
}
