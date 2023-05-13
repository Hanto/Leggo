package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;

@Data
public class CreateNonFederatedOrderCommand
{
    private final Year year;
    private final ProducerId producerId;
    private final ProductId productId;
}
