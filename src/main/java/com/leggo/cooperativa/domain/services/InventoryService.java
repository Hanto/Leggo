package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderTaxed;
import com.leggo.cooperativa.domain.repositories.BuyOrderRepository;
import com.leggo.cooperativa.domain.repositories.SellOrderRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryService
{
    private final BuyOrderRepository buyOrderRepository;
    private final SellOrderRepository sellOrderRepository;

    public SellOrder exitProductFrom(SellOrderTaxed orderDemand)
    {
        Kilogram kilogramsInInventory = totalKilogramsInStock(orderDemand.getYearOfHarvest(), orderDemand.getProductId());

        if (kilogramsInInventory.isLess(orderDemand.getQuantity()) )
            throw new RuntimeException(String.format("Not enough inventory to serve order: %s", kilogramsInInventory));

        SellOrder sellOrder = buildSellOrder(orderDemand);

        sellOrderRepository.addSellOrder(sellOrder);

        return sellOrder;
    }

    public void enterProductFrom(FederatedOrder buyOrder)
    {
        buyOrderRepository.addFederatedOrder(buyOrder);
    }

    public void enterProductFrom(NonFederatedOrder buyOrder)
    {
        buyOrderRepository.addNonFederatedOrder(buyOrder);
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

    private Kilogram totalKilogramsInStock(Year year, ProductId productId)
    {
        Kilogram kilogramsBought = totalKilogramsBought(year, productId);
        Kilogram kilogramsSold = totalKilogramsSold(year, productId);
        return kilogramsBought.minus(kilogramsSold);
    }

    private Kilogram totalKilogramsBought(Year year, ProductId productId)
    {
        Kilogram fromFederatedOrder = buyOrderRepository.findFederatedOrderBy(year, productId)
            .map(FederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = buyOrderRepository.findNonFederatedOrdersBy(year, productId).stream()
            .map(NonFederatedOrder::getTotalKilograms)
            .reduce(Kilogram.of(0), Kilogram::sum);

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }

    private Kilogram totalKilogramsBoughtFrom(Year year, ProductId productId, ProducerId producerId)
    {
        Kilogram fromFederatedOrder = buyOrderRepository.findFederatedOrderBy(year, productId)
            .map(federatedOrder -> federatedOrder.getContributionOf(producerId))
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = buyOrderRepository.findNonFederatedOrderBy(year, productId, producerId)
            .map(NonFederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }

    private Kilogram totalKilogramsSold(Year year, ProductId productId)
    {
        return sellOrderRepository.findSellOrdersBy(year, productId).stream()
            .map(SellOrder::getQuantity)
            .reduce(Kilogram.of(0), Kilogram::sum);
    }

    private static SellOrder buildSellOrder(SellOrderTaxed orderDemand)
    {
        return SellOrder.builder()
            .sellOrderId(new SellOrderId())
            .yearOfHarvest(orderDemand.getYearOfHarvest())
            .productId(orderDemand.getProductId())
            .quantity(orderDemand.getQuantity())
            .marketRateDay(orderDemand.getMarketRateDay())
            .distance(orderDemand.getDistance())
            .pricePerKilogram(orderDemand.getProductPrice())
            .logisticsPrice(orderDemand.getLogisticsPrice())
            .taxes(orderDemand.getTaxes())
            .build();
    }
}
