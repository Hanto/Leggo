package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Kilograms
{
    private final Float amount;

    public static Kilograms of(Float amount)
    {
        return new Kilograms(amount);
    }

    public boolean isZero()
    {
        return amount == 0.0f;
    }
}
