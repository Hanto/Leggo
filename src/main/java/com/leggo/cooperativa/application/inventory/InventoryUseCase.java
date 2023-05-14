package com.leggo.cooperativa.application.inventory;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.SellerRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryUseCase
{
    private final SellerRepository sellerRepository;

    public Kilogram totalKilogramsBought(Year year, ProductId productId)
    {
        Kilogram fromFederatedOrder = sellerRepository.findFederatedOrdersBy(year, productId)
            .map(FederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = sellerRepository.findNonFederatedOrdersBy(year, productId).stream()
            .map(NonFederatedOrder::getTotalKilograms)
            .reduce(Kilogram.of(0), Kilogram::sum);

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }

    public Kilogram totalKilogramsBoughtFrom(Year year, ProductId productId, ProducerId producerId)
    {
        Kilogram fromFederatedOrder = sellerRepository.findFederatedOrdersBy(year, productId)
            .map(federatedOrder -> federatedOrder.getContributionOf(producerId))
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = sellerRepository.findNonFederatedOrderBy(year, producerId,productId)
            .map(NonFederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

        return fromFederatedOrder.sum(fromNonFederatedOrders);
    }
}
