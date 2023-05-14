package com.leggo.cooperativa.domain.repositories;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface SellerRepository
{
    void addFederatedSeller(FederatedOrder seller);
    void addNonFederatedSeller(NonFederatedOrder seller);

    Optional<FederatedOrder> findFederatedOrderBy(Year year, ProductId productId);
    Optional<NonFederatedOrder> findNonFederatedOrderBy(Year year, ProductId product, ProducerId producerId);

    void setMaxHectaresForSmallProducer(Year year, Hectare maxHectares);
    Optional<Hectare> maxHectaresForSmallProducer(Year year);

    int numberOfNonFederatedOrders(Year year, ProducerId producerId);
    Optional<FederatedOrder>findFederatedOrdersBy(Year year, ProductId productId);
    List<NonFederatedOrder> findNonFederatedOrdersBy(Year year, ProductId productId);
    Optional<NonFederatedOrder>findNonFederatedOrderBy(Year year, ProducerId producerId, ProductId productId);
}
