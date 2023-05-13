package com.leggo.cooperativa.domain.model.producer;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class Field
{
    @Getter private final ProductId productId;
    @Getter private final Hectare hectares;

    public static Field createField(ProductId productId, Hectare hectares)
    {
        return new Field(productId, hectares);
    }
}
