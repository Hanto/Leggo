package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class KilogramsPerHectare
{
    private final double amount;

    public static KilogramsPerHectare of(double amount)
    {
        return new KilogramsPerHectare(amount);
    }

    public Kilogram multiply(Hectare hectare)
    {
        return Kilogram.of(amount * hectare.getAmount());
    }
}
