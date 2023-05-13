package com.leggo.cooperativa.domain.model.seller;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.common.Year;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor @Getter @ToString
public class FederatedOrder implements BuyOrder
{
    private final BuyOrderId buyOrderId;
    private final Year year;
    private final Set<Producer> producers;
    private final Product product;
    private final LocalDateTime soldTime;

    public boolean containsTheProducer(Producer producerId)
    {
        return producers.contains(producerId);
    }

    public Hectare getTotalHectares()
    {
        return producers.stream()
            .map(producer -> producer.getTotalHectaresFor(year, product.getProductId()))
            .reduce(Hectare.ofZero(), Hectare::sum);
    }
}
