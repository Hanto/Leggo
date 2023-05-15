package com.leggo.cooperativa.infrastructure.rest.requests;

import com.leggo.cooperativa.application.buyorder.CreateNonFederatedOrderCommand;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CreateNonFederatedOrderRequest
{
    @NotNull private final Integer year;
    @NotNull private final String producerId;
    @NotNull private final String productId;

    public CreateNonFederatedOrderCommand toCommand()
    {
        return CreateNonFederatedOrderCommand.builder()
            .year(Year.of(year))
            .producerId(ProducerId.of(producerId))
            .productId(ProductId.of(productId))
            .build();
    }
}
