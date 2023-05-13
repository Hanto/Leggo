package com.leggo.cooperativa.infrastructure.repositories;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.seller.NonFederatedOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NonFederatedOrdersContainer
{
    private final Map<Year, Map<ProducerId, List<NonFederatedOrder>>> rootMap = new HashMap<>();

    public void add(NonFederatedOrder seller)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.computeIfAbsent(seller.getYear(), year -> new HashMap<>());
        List<NonFederatedOrder>list = innerMap.computeIfAbsent(seller.getProducer().getProducerId(), productId -> new ArrayList<>());
        list.add(seller);
    }

    public Optional<NonFederatedOrder> findBy(Year year, ProducerId producerId, ProductId productId)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.get(year);

        List<NonFederatedOrder>sellers = innerMap != null ?
            innerMap.getOrDefault(producerId, List.of()) :
            List.of();

       return sellers.stream()
            .filter(nonFederatedSeller -> nonFederatedSeller.getProduct().equals(productId))
            .findFirst();
    }

    public List<NonFederatedOrder>findBy(Year year, ProducerId producerId)
    {
        Map<ProducerId, List<NonFederatedOrder>>innerMap = rootMap.get(year);

        return innerMap != null ?
            innerMap.getOrDefault(producerId, List.of()) :
            List.of();
    }
}
