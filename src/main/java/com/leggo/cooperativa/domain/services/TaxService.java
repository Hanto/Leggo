package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Tax;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderTaxed;

public class TaxService
{
    private static final Tax DEFAULT_TAX = Tax.of(15);
    public SellOrderTaxed applyCorrectTax(SellOrderLogisticPriced order)
    {
        return SellOrderTaxed.builder()
            .yearOfHarvest(order.getYearOfHarvest())
            .productId(order.getProductId())
            .productPrice(order.getProductPrice())
            .quantity(order.getQuantity())
            .marketRateDay(order.getMarketRateDay())
            .distance(order.getDistance())
            .productPrice(order.getProductPrice())
            .logisticsPrice(order.getLogisticsPrice())
            .taxes(DEFAULT_TAX).build();
    }
}
