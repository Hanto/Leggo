package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Price;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePerKilogram
{
    private final BigDecimal amount;

    public static PricePerKilogram of(BigDecimal bigDecimal)
    {
        return new PricePerKilogram(bigDecimal);
    }

    public static PricePerKilogram of(String string)
    {
        return new PricePerKilogram(new BigDecimal(string));
    }

    public Price multiply(Kilogram kilograms)
    {
        return Price.of(this.amount.multiply(BigDecimal.valueOf(kilograms.getAmount())));
    }

    public PricePerKilogram multiply(double factor)
    {
        return PricePerKilogram.of(amount.multiply(BigDecimal.valueOf(factor)));
    }
}
