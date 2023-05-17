package com.leggo.cooperativa.infrastructure.repositories.memory;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellOrderContainer
{
    private final Map<Year, Map<ProductId, List<SellOrder>>> rootMap = new HashMap<>();

    public void add(SellOrder order)
    {
        Map<ProductId, List<SellOrder>>innerMap = rootMap.computeIfAbsent(order.getYearOfHarvest(), year -> new HashMap<>());
        List<SellOrder>list = innerMap.computeIfAbsent(order.getProductId(), productId -> new ArrayList<>());
        list.add(order);
    }

    public List<SellOrder>findBy(Year year, ProductId productId)
    {
        Map<ProductId, List<SellOrder>>innerMap =  rootMap.get(year);

        return innerMap != null ?
            innerMap.getOrDefault(productId, List.of()) :
            List.of();
    }
}
