package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateFederatedOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data @AllArgsConstructor
public class CreatedFederatedOrderCommand
{
    private final Set<ProducerId> producersIds;
    private final ProductId productId;
    private final Year year;

    public CreatedFederatedOrderCommand(CreateFederatedOrderRequest request)
    {
        this.producersIds = request.getProducerIds().stream()
            .map(ProducerId::new)
            .collect(Collectors.toSet());

        this.productId = new ProductId(request.getProductId());
        this.year = new Year(request.getYear());
    }
}
