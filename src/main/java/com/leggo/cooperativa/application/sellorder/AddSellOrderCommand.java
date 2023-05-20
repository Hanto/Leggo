package com.leggo.cooperativa.application.sellorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class AddSellOrderCommand
{
    private final SellOrderId sellOrderId;
    private final Year yearOfHavest;
    private final ProductId productId;
    private final Kilogram quantity;
    private final LocalDate marketRateDay;
    private final Kilometer distance;
}
