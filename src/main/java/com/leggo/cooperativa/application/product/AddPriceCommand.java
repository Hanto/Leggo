package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Price;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPriceCommand
{
    private final ProductId productId;
    private final Price price;
    private final LocalDate day;
}
