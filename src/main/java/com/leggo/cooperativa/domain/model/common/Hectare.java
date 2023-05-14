package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Hectare
{
    private final float amount;

    public static Hectare of(float amount)
    {
        return new Hectare(amount);
    }

    public static Hectare ofZero()
    {
        return new Hectare(0f);
    }

    public Hectare sum(Hectare other)
    {
        return new Hectare(this.amount + other.amount);
    }

    public boolean isGreater(Hectare other)
    {
        return this.getAmount() > other.getAmount();
    }

}
