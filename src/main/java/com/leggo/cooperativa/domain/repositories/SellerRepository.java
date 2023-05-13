package com.leggo.cooperativa.domain.repositories;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.seller.FederatedOrder;
import com.leggo.cooperativa.domain.model.seller.NonFederatedOrder;

import java.util.Optional;

public interface SellerRepository
{
    void addFederatedSeller(FederatedOrder seller);
    void addNonFederatedSeller(NonFederatedOrder seller);

    Optional<FederatedOrder> findFederatedSellerBy(Year year, ProductId productId);
    Optional<NonFederatedOrder>findNonFederatedSellerBy(Year year, ProductId product, ProducerId producerId);

    void setMaxHectaresForSmallProducer(Year year, Hectare maxHectares);
    Optional<Hectare> maxHectaresForSmallProducer(Year year);

    int numberOfNonFederatedOrders(Year year, ProducerId producerId);
}
