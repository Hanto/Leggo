package com.leggo.cooperativa.domain.model.seller;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor @Getter @ToString
public class NonFederatedOrder implements BuyOrder
{
    private final BuyOrderId buyOrderId;
    private final Year year;
    private final ProducerId producerId;
    private final ProductId productId;
    private final LocalDateTime soldTime;
    private final Hectare hectares;
}
