package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Price;
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
    private final Price price;
    private final LocalDate day;

    public AddPriceCommand(AddPriceRequest request)
    {
        this.productId = new ProductId(request.getProductId());
        this.price = new Price(new BigDecimal(request.getPrice()));
        this.day = request.getDay();
    }
}
