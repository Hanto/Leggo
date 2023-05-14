package com.leggo.cooperativa.domain.model.product;

import lombok.Data;

@Data
public class ProductId
{
    private final String id;

    public static ProductId of(String id)
    {
        return new ProductId(id);
    }
}
