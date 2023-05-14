package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Kilometer
{
    public final float amount;

    public static Kilometer of(float amount)
    {
        if (amount < 0)
            throw new IllegalArgumentException("Distances cannot be negative");

        return new Kilometer(amount);
    }

    public boolean isZero()
    {
        return amount <= 0;
    }
}
