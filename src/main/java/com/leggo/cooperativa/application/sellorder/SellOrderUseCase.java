package com.leggo.cooperativa.application.sellorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderDemanded;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderTaxed;
import com.leggo.cooperativa.domain.services.InventoryService;
import com.leggo.cooperativa.domain.services.LogisticsService;
import com.leggo.cooperativa.domain.services.PriceService;
import com.leggo.cooperativa.domain.services.TaxService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellOrderUseCase
{
    private final PriceService priceService;
    private final LogisticsService logistics;
    private final InventoryService inventory;
    private final TaxService taxService;

    private SellOrder createSellOrder(AddSellOrderCommand command)
    {
        SellOrderDemanded orderDemanded = buildDemand(command);
        SellOrderProductPriced orderProductPriced = priceService.price(orderDemanded);
        SellOrderLogisticPriced orderLogisticPriced = logistics.calculateLogistic(orderProductPriced);
        SellOrderTaxed orderTaxed = taxService.applyCorrectTax(orderLogisticPriced);
        return inventory.serveSellOrder(orderTaxed);
    }

    public SellOrder createSellOrderForDistributor(AddSellOrderCommand command)
    {
        return createSellOrder(command);
    }

    private static final Kilogram MAX_FOR_MINORIST = Kilogram.of(100);
    public SellOrder createSellOrderForMinorist(AddSellOrderCommand command)
    {
        if (command.getQuantity().isGreaterOrEqual(MAX_FOR_MINORIST))
            throw new IllegalArgumentException("Cannot sell more than 100Kg to minorists");

        return createSellOrder(command);
    }

    private SellOrderDemanded buildDemand(AddSellOrderCommand command)
    {
        return SellOrderDemanded.builder()
            .yearOfHarvest(command.getYearOfHavest())
            .productId(command.getProductId())
            .quantity(command.getQuantity())
            .marketRateDay(command.getMarketRateDay())
            .distance(command.getDistance())
            .build();
    }
}
