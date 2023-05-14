package com.leggo.cooperativa.domain.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString @RequiredArgsConstructor
public abstract class Product
{
    @Getter private final ProductId productId;
    @Getter private final String name;
    @Getter private final float getTonsPerHectare;
    private final MarketRate marketRates;

    public PricePerKilogram lastMarketPrice(LocalDate day)
    {
        return marketRates.lastPriceFor(day);
    }

    public void addMarketPrice(BigDecimal amount, LocalDate day)
    {
        PricePerKilogram pricePerKilogram = new PricePerKilogram(amount);
        marketRates.addOrReplacePrice(pricePerKilogram, day);
    }
}
