package com.leggo.cooperativa.infrastructure.repositories.memory;

import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NonFederatedOrdersContainer
{
    private final Map<Year, Map<ProducerId, List<NonFederatedOrder>>> rootMap = new HashMap<>();
    private final Map<Year, Map<ProductId, List<NonFederatedOrder>>>rootMapByProduct = new HashMap<>();

    public void add(NonFederatedOrder seller)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.computeIfAbsent(seller.getYear(), year -> new HashMap<>());
        List<NonFederatedOrder>list = innerMap.computeIfAbsent(seller.getContributor().getProducerId(), producerId -> new ArrayList<>());
        list.add(seller);

        Map<ProductId, List<NonFederatedOrder>>innerMapByProduct = rootMapByProduct.computeIfAbsent(seller.getYear(), year -> new HashMap<>());
        List<NonFederatedOrder>listByProduct = innerMapByProduct.computeIfAbsent(seller.getProductId(), productId -> new ArrayList<>());
        listByProduct.add(seller);
    }

    public Optional<NonFederatedOrder> findBy(Year year, ProducerId producerId, ProductId productId)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.get(year);

        List<NonFederatedOrder>sellers = innerMap != null ?
            innerMap.getOrDefault(producerId, List.of()) :
            List.of();

       return sellers.stream()
            .filter(nonFederatedSeller -> nonFederatedSeller.getProductId().equals(productId))
            .findFirst();
    }

    public List<NonFederatedOrder>findBy(Year year, ProducerId producerId)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.get(year);

        return innerMap != null ?
            innerMap.getOrDefault(producerId, List.of()) :
            List.of();
    }

    public List<NonFederatedOrder>findBy(Year year, ProductId productId)
    {
        Map<ProductId, List<NonFederatedOrder>>innerMap = rootMapByProduct.get(year);

        return innerMap != null ?
            innerMap.getOrDefault(productId, List.of()) :
            List.of();
    }
}
