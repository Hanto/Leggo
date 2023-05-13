package com.leggo.cooperativa.infrastructure.repositories;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.seller.FederatedOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FederatedOrdersContainer
{
    Map<Year, Map<ProductId, FederatedOrder>> rootMap = new HashMap<>();

    public void add(FederatedOrder seller)
    {
        Map<ProductId, FederatedOrder>innerMap = rootMap.computeIfAbsent(seller.getYear(), year -> new HashMap<>());

        innerMap.put(seller.getProduct().getProductId(), seller);
    }

    public Optional<FederatedOrder> findBy(Year year, ProductId productId)
    {
        Map<ProductId, FederatedOrder>innerMap = rootMap.get(year);
        return innerMap == null ? Optional.empty() : Optional.ofNullable(innerMap.get(productId));
    }
}
