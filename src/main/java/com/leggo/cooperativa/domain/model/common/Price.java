package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price
{
    private final BigDecimal price;

    public static Price of(String string)
    {
        return new Price(new BigDecimal(string));
    }
}
