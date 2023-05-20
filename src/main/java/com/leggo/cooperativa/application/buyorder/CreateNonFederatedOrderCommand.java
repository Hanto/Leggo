package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @AllArgsConstructor @Builder
public class CreateNonFederatedOrderCommand
{
    private final BuyOrderId buyOrderId;
    private final Year year;
    private final ProducerId producerId;
    private final ProductId productId;
}
