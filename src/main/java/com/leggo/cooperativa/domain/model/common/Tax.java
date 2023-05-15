package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Tax
{
    public final double percentage;

    public static Tax of(double percentage)
    {
        return new Tax(percentage);
    }
}
