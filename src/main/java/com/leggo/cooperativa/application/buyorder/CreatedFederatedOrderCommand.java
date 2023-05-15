package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data @AllArgsConstructor @Builder
public class CreatedFederatedOrderCommand
{
    private final Set<ProducerId> producersIds;
    private final ProductId productId;
    private final Year year;
}
