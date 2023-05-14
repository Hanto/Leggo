package com.leggo.cooperativa.domain.model.logistics;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePerKilogramAndKilometer
{
    private final BigDecimal amount;

    public static PricePerKilogramAndKilometer of(String string)
    {
        return new PricePerKilogramAndKilometer(new BigDecimal(string));
    }

    public static PricePerKilogramAndKilometer of(BigDecimal bigDecimal)
    {
        return new PricePerKilogramAndKilometer(bigDecimal);
    }

    public Price multiply(Kilogram kilogram, Kilometer kilometer)
    {
        return Price.of(amount.multiply(BigDecimal.valueOf(kilogram.getAmount())).multiply(BigDecimal.valueOf(kilometer.getAmount())) );
    }

}
