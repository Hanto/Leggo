package com.leggo.cooperativa.infrastructure.rest.requests;

import com.leggo.cooperativa.application.buyorder.CreatedFederatedOrderCommand;
import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CreateFederatedOrderRequest
{
    @NotNull private final String buyOrderUUID;
    @NotNull private final Set<String>producerIds;
    @NotNull private final String productId;
    @NotNull private final Integer year;

    public CreatedFederatedOrderCommand toCommand()
    {
        Set<ProducerId> convertedProducerIds = producerIds.stream()
            .map(ProducerId::new)
            .collect(Collectors.toSet());

        return CreatedFederatedOrderCommand.builder()
            .buyOrderId(BuyOrderId.of(buyOrderUUID))
            .producersIds(convertedProducerIds)
            .productId(ProductId.of(productId))
            .year(Year.of(year))
            .build();
    }
}
