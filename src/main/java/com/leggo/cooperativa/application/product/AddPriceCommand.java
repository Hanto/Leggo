package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @AllArgsConstructor @Builder
public class AddPriceCommand
{
    private final ProductId productId;
    private final PricePerKilogram pricePerKilogram;
    private final LocalDate day;
}
