package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.rest.requests.AddPriceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @AllArgsConstructor
public class AddPriceCommand
{
    private final ProductId productId;
    private final PricePerKilogram pricePerKilogram;
    private final LocalDate day;

    public AddPriceCommand(AddPriceRequest request)
    {
        this.productId = new ProductId(request.getProductId());
        this.pricePerKilogram = new PricePerKilogram(new BigDecimal(request.getPricePerKilogram()));
        this.day = request.getDay();
    }
}
