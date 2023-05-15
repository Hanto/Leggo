package com.leggo.cooperativa.infrastructure.rest.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leggo.cooperativa.application.product.AddPriceCommand;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class AddPriceRequest
{
    @NotNull private final String productId;
    @NotNull private final String pricePerKilogram;
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull private final LocalDate day;

    public AddPriceCommand toCommand()
    {
        return AddPriceCommand.builder()
            .productId(ProductId.of(productId))
            .pricePerKilogram(PricePerKilogram.of(pricePerKilogram))
            .day(day)
            .build();
    }
}
