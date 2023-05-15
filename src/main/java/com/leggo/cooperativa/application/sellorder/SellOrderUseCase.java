package com.leggo.cooperativa.application.sellorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderDemanded;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.repositories.SellOrderRepository;
import com.leggo.cooperativa.domain.services.InventoryService;
import com.leggo.cooperativa.domain.services.PriceService;
import com.leggo.cooperativa.domain.services.logistics.LogisticCalculatorService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SellOrderUseCase
{
    private final PriceService priceService;
    private final LogisticCalculatorService logistics;
    private final InventoryService inventory;
    private final SellOrderRepository sellOrderRepository;

    public void createSellOrder(Year yearOfHarvest, ProductId productId, Kilogram quantity, LocalDate marketRateDay, Kilometer distance)
    {
        SellOrderDemanded orderDemanded = buildDemand(yearOfHarvest, productId, quantity, marketRateDay, distance);
        SellOrderProductPriced orderPriced = priceService.price(orderDemanded);
        SellOrderLogisticPriced orderLogisticPriced = logistics.calculateLogistic(orderPriced);
        inventory.serveSellOrder(orderLogisticPriced);
    }

    private static SellOrderDemanded buildDemand(Year yearOfHarvest, ProductId productId, Kilogram quantity, LocalDate marketRateDay, Kilometer distance)
    {
        return SellOrderDemanded.builder()
            .yearOfHarvest(yearOfHarvest)
            .productId(productId)
            .totalKilograms(quantity)
            .marketRateDay(marketRateDay)
            .distance(distance)
            .build();
    }
}
