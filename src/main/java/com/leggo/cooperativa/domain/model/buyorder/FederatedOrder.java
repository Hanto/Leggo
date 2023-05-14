package com.leggo.cooperativa.domain.model.buyorder;

import com.leggo.cooperativa.domain.model.common.Kilograms;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
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
    private final Set<ProducerId> producerIds;
    private final ProductId productId;
    private final LocalDateTime soldTime;
    private final Kilograms kilograms;

    public boolean containsTheProducer(ProducerId producerId)
    {
        return producerIds.contains(producerId);
    }
}
