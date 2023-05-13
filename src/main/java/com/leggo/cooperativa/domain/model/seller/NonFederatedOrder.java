package com.leggo.cooperativa.domain.model.seller;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.product.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor @Getter @ToString
public class NonFederatedOrder implements BuyOrder
{
    private final BuyOrderId buyOrderId;
    private final Year year;
    private final Producer producer;
    private final Product product;
    private final LocalDateTime soldTime;

    public Hectare getTotalHectares()
    {
        return producer.getTotalHectaresFor(year, product.getProductId());
    }
}
