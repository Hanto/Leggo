package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import lombok.Data;

@Data
public class KilogramsPerHectare
{
    private final double amount;

    public Kilogram multiply(Hectare hectare)
    {
        return Kilogram.of(amount * hectare.getAmount());
    }
}
