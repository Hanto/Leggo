package com.leggo.cooperativa.infrastructure.repositories;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrder;
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.SellerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryDatabase implements ProductRepository, ProducerRepository, SellerRepository
{
    private final Map<ProductId, Product>productCatalog = new HashMap<>();
    private final Map<ProducerId, Producer>producers = new HashMap<>();
    private final Map<Year, Hectare>maxHarvestByYear = new HashMap<>();
    private final FederatedOrdersContainer federatedSellers = new FederatedOrdersContainer();
    private final NonFederatedOrdersContainer nonFederatedSellers = new NonFederatedOrdersContainer();

    @Override
    public void addProduct(Product product)
    {
        productCatalog.put(product.getProductId(), product);
    }

    @Override
    public Optional<Product>findProductById(ProductId productId)
    {
        return Optional.ofNullable(productCatalog.get(productId));
    }

    @Override
    public void updateProduct(Product product)
    {
        // nothing to do here
    }

    @Override
    public void addProducer(Producer producer)
    {
        producers.put(producer.getProducerId(), producer);
    }

    @Override
    public Optional<Producer>findProducerById(ProducerId producerId)
    {
        return Optional.ofNullable(producers.get(producerId));
    }

    @Override
    public Optional<Hectare> maxHectaresForSmallProducer(Year year)
    {
        return Optional.ofNullable(maxHarvestByYear.get(year));
    }

    @Override
    public void setMaxHectaresForSmallProducer(Year year, Hectare maxHectares)
    {
        maxHarvestByYear.put(year, maxHectares);
    }

    @Override
    public void updateProducer(Producer producer)
    {
        // nothing to do here
    }

    @Override
    public void addFederatedSeller(FederatedOrder seller)
    {
        federatedSellers.add(seller);
    }

    @Override
    public Optional<FederatedOrder> findFederatedOrderBy(Year year, ProductId productId)
    {
        return federatedSellers.findBy(year, productId);
    }

    @Override
    public void addNonFederatedSeller(NonFederatedOrder seller)
    {
       nonFederatedSellers.add(seller);
    }

    @Override
    public Optional<NonFederatedOrder> findNonFederatedOrderBy(Year year, ProductId productId, ProducerId producerId)
    {
        return nonFederatedSellers.findBy(year, producerId, productId);
    }

    @Override
    public int numberOfNonFederatedOrders(Year year, ProducerId producerId)
    {
        return nonFederatedSellers.findBy(year, producerId).size();
    }

    @Override
    public Optional<FederatedOrder>findFederatedOrdersBy(Year year, ProductId productId)
    {
        return federatedSellers.findBy(year, productId);
    }

    @Override
    public Optional<NonFederatedOrder>findNonFederatedOrderBy(Year year, ProducerId producerId, ProductId productId)
    {
        return nonFederatedSellers.findBy(year, producerId,productId);
    }

    @Override
    public List<NonFederatedOrder>findNonFederatedOrdersBy(Year year, ProductId productId)
    {
        return nonFederatedSellers.findBy(year, productId);
    }

    public Kilogram totalKilogramsBought(Year year, ProductId productId)
    {
        Optional<FederatedOrder> maybeFederatedOrder = federatedSellers.findBy(year, productId);
        List<NonFederatedOrder> nonFederatedOrders = nonFederatedSellers.findBy(year, productId);

        List<BuyOrder>allOrders = new ArrayList<>(nonFederatedOrders);
        allOrders.add(maybeFederatedOrder.orElse(null));

        return allOrders.stream()
            .filter(Objects::nonNull)
            .map(BuyOrder::getTotalKilograms)
            .reduce(Kilogram.of(0), Kilogram::sum);
    }

    public Kilogram totalKilogramsBoughtFrom(Year year, ProductId productId, ProducerId producerId)
    {
        Kilogram fromFederatedOrder = federatedSellers.findBy(year, productId)
            .map(federatedOrder -> federatedOrder.getContributionOf(producerId))
            .orElse(Kilogram.of(0));

        Kilogram fromNonFederatedOrders = nonFederatedSellers.findBy(year, producerId,productId)
            .map(NonFederatedOrder::getTotalKilograms)
            .orElse(Kilogram.of(0));

       return fromFederatedOrder.sum(fromNonFederatedOrders);
    }
}
