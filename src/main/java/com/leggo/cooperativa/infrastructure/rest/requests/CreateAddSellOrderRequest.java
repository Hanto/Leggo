package com.leggo.cooperativa.infrastructure.rest.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leggo.cooperativa.application.sellorder.AddSellOrderCommand;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class CreateAddSellOrderRequest
{
    @NotNull private final String sellOrderId;
    @NotNull private final Integer yearOfHarvest;
    @NotNull private final String productId;
    @NotNull private final Double quantity;
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull private final LocalDate marketRateDay;
    @NotNull private final Double distance;

    public AddSellOrderCommand toCommand()
    {
        return AddSellOrderCommand.builder()
            .sellOrderId(SellOrderId.of(sellOrderId))
            .yearOfHavest(Year.of(yearOfHarvest))
            .productId(ProductId.of(productId))
            .quantity(Kilogram.of(quantity))
            .marketRateDay(marketRateDay)
            .distance(Kilometer.of(distance))
            .build();
    }
}
