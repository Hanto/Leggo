package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateNonFederatedOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CreateNonFederatedOrderCommand
{
    private final Year year;
    private final ProducerId producerId;
    private final ProductId productId;

    public CreateNonFederatedOrderCommand(CreateNonFederatedOrderRequest request)
    {
        this.year = new Year(request.getYear());
        this.producerId = new ProducerId(request.getProducerId());
        this.productId = new ProductId(request.getProductId());
    }
}
