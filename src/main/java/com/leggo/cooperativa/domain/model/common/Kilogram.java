package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Kilogram
{
    private final double amount;

    public static Kilogram of(double amount)
    {
        return new Kilogram(amount);
    }

    public boolean isZeroOrLess()
    {
        return amount <= 0.0f;
    }

    public boolean isMinusOrEqual(Kilogram other)
    {
        return amount <= other.amount;
    }

    public boolean isLess(Kilogram other)
    {
        return amount < other.amount;
    }

    public Kilogram minus(Kilogram other)
    {
        return Kilogram.of(amount - other.amount);
    }

    public double divide(Kilogram other)
    {
        return this.amount/other.amount;
    }

    public Kilogram modulus(Kilogram other)
    {
        return Kilogram.of(this.amount % other.getAmount());
    }

    public Kilogram sum(Kilogram other)
    {
        return Kilogram.of(this.amount + other.amount);
    }
}
