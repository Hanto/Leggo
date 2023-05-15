package com.leggo.cooperativa.domain.repositories;

import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;

import java.util.List;

public interface SellOrderRepository
{
    void addSellOrder(SellOrder order);
    List<SellOrder>findSellOrdersBy(Year year, ProductId productId);
}
