package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.repositories.BuyOrderRepository;
import com.leggo.cooperativa.domain.repositories.SellOrderRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryService
{
    private final BuyOrderRepository buyOrderRepository;
    private final SellOrderRepository sellOrderRepository;

    public void serveSellOrder(SellOrderLogisticPriced orderDemand)
    {
        Kilogram kilogramsBought = totalKilogramsBought(orderDemand.getYearOfHarvest(), orderDemand.getProductId());
        Kilogram kilogramsSold = totalKilogramsSold(orderDemand.getYearOfHarvest(), orderDemand.getProductId());
        Kilogram kilogramsInInventory = kilogramsBought.minus(kilogramsSold);

        if (kilogramsInInventory.isLess(orderDemand.getQuantity()) )
            throw new RuntimeException("Not enough inventory to serve order");

        SellOrder sellOrder = buildSellOrder(orderDemand);
        sellOrderRepository.addSellOrder(sellOrder);
    }

    public Kilogram totalKilogramsBought(Year year, ProductId productId)
    {
        Kilogram fromFederatedOrder = buyOrderRepository.findFederatedOrdersBy(year, productId)
            .map(FederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = buyOrderRepository.findNonFederatedOrdersBy(year, productId).stream()
            .map(NonFederatedOrder::getTotalKilograms)
            .reduce(Kilogram.of(0), Kilogram::sum);

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }

    public Kilogram totalKilogramsBoughtFrom(Year year, ProductId productId, ProducerId producerId)
    {
        Kilogram fromFederatedOrder = buyOrderRepository.findFederatedOrdersBy(year, productId)
            .map(federatedOrder -> federatedOrder.getContributionOf(producerId))
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = buyOrderRepository.findNonFederatedOrderBy(year, producerId,productId)
            .map(NonFederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }

    public Kilogram totalKilogramsSold(Year year, ProductId productId)
    {
        return sellOrderRepository.findSellOrdersBy(year, productId).stream()
            .map(SellOrder::getQuantity)
            .reduce(Kilogram.of(0), Kilogram::sum);
    }

    private static SellOrder buildSellOrder(SellOrderLogisticPriced orderDemand)
    {
        return SellOrder.builder()
            .id(new SellOrderId())
            .yearOfHarvest(orderDemand.getYearOfHarvest())
            .productId(orderDemand.getProductId())
            .quantity(orderDemand.getQuantity())
            .marketRateDay(orderDemand.getMarketRateDay())
            .distance(orderDemand.getDistance())
            .productPrice(orderDemand.getProductPrice())
            .logisticsPrice(orderDemand.getLogisticsPrice())
            .build();
    }
}
