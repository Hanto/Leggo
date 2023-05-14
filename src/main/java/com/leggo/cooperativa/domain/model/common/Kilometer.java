package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Kilometer
{
    public final double amount;

    public static Kilometer of(double amount)
    {
        return new Kilometer(amount);
    }

    public static Kilometer ofZero()
    {
        return new Kilometer(0f);
    }

    public boolean isZeroOrLess()
    {
        return amount <= 0;
    }

    public Kilometer minus(Kilometer other)
    {
        return Kilometer.of(amount - other.amount);
    }

    public Kilometer modulus(Kilometer other)
    {
        return Kilometer.of(amount % other.amount);
    }

    public double divide(Kilometer other)
    {
        return (amount / other.amount);
    }
}
