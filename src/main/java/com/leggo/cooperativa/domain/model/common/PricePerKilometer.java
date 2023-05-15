package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePerKilometer
{
    private final BigDecimal amount;

    public static PricePerKilometer of(BigDecimal bigDecimal)
    {
        return new PricePerKilometer(bigDecimal);
    }

    public static PricePerKilometer of(String string)
    {
        return new PricePerKilometer(new BigDecimal(string));
    }

    public Price multiply(Kilometer kilometer)
    {
        return Price.of(this.amount.multiply(BigDecimal.valueOf(kilometer.getAmount())));
    }
}
