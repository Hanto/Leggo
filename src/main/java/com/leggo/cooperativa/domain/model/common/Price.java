package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price
{
    private final BigDecimal amount;

    public static Price of(String string)
    {
        return new Price(new BigDecimal(string));
    }

    public static Price of(BigDecimal bigDecimal)
    {
        return new Price(bigDecimal);
    }

    public Price multiply(Double factor)
    {
        return Price.of(amount.multiply(BigDecimal.valueOf(factor)));
    }

    public Price add(Price other)
    {
        return Price.of(amount.add(other.amount));
    }

    public Price applyTaxes(Tax tax)
    {
        double factor = (tax.percentage + 100)/100;

        return Price.of(amount.multiply(BigDecimal.valueOf(factor)));
    }
}
