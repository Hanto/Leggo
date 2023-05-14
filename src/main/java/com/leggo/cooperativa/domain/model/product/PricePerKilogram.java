package com.leggo.cooperativa.domain.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString @EqualsAndHashCode
public class PricePerKilogram
{
    @Getter
    private final BigDecimal amount;

    public static PricePerKilogram of(BigDecimal bigDecimal)
    {
        return new PricePerKilogram(bigDecimal);
    }

    public static PricePerKilogram of(String string)
    {
        return new PricePerKilogram(new BigDecimal(string));
    }

    public static PricePerKilogram ofZero()
    {
        return new PricePerKilogram(BigDecimal.ZERO);
    }

    public PricePerKilogram(BigDecimal amount)
    {   this.amount = amount; }

    public PricePerKilogram multiply(Float number)
    {
        return PricePerKilogram.of(this.amount.multiply(BigDecimal.valueOf(number)));
    }
}
