package com.leggo.cooperativa.domain.model.product;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product
{
    @Getter
    private final ProductId productId;
    @Getter
    private final String name;
    @Getter
    private final float productionPerHectare;
    private final MarketRate marketRates;

    private Product(ProductId productId, String name, float productionPerHectare, MarketRate cotizaciones)
    {
        this.productId = productId; this.name = name;
        this.productionPerHectare = productionPerHectare;
        this.marketRates = cotizaciones;
    }

    public static Product createProduct(ProductId productId, String name, float productionPerHectare, Price initialPrice)
    {
        MarketRate cotizaciones = new MarketRate(initialPrice);
        return new Product(productId, name, productionPerHectare, cotizaciones);
    }

    public Price lastPriceFor(LocalDate day)
    {
        return marketRates.lastPriceFor(day);
    }

    public void addPriceFor(BigDecimal amount, LocalDate day)
    {
        Price price = new Price(amount);
        marketRates.addOrReplacePrice(price, day);
    }
}
