package com.leggo.cooperativa.infrastructure.repositories;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;
import com.leggo.cooperativa.domain.repositories.BuyOrderRepository;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.SellOrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryDatabase implements ProductRepository, ProducerRepository, BuyOrderRepository,
    SellOrderRepository
{
    private final Map<ProductId, Product>productCatalog = new HashMap<>();
    private final Map<ProducerId, Producer>producers = new HashMap<>();
    private final Map<Year, Hectare>maxHarvestByYear = new HashMap<>();
    private final FederatedOrdersContainer federatedOrders = new FederatedOrdersContainer();
    private final NonFederatedOrdersContainer nonFederatedOrders = new NonFederatedOrdersContainer();
    private final SellOrderContainer sellOrderContainer = new SellOrderContainer();

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
    public void addFederatedOrder(FederatedOrder order)
    {
        federatedOrders.add(order);
    }

    @Override
    public Optional<FederatedOrder> findFederatedOrderBy(Year year, ProductId productId)
    {
        return federatedOrders.findBy(year, productId);
    }

    @Override
    public void addNonFederatedOrder(NonFederatedOrder order)
    {
       nonFederatedOrders.add(order);
    }

    @Override
    public Optional<NonFederatedOrder> findNonFederatedOrderBy(Year year, ProductId productId, ProducerId producerId)
    {
        return nonFederatedOrders.findBy(year, producerId, productId);
    }

    @Override
    public int numberOfNonFederatedOrdersFrom(Year year, ProducerId producerId)
    {
        return nonFederatedOrders.findBy(year, producerId).size();
    }

    @Override
    public Optional<FederatedOrder>findFederatedOrdersBy(Year year, ProductId productId)
    {
        return federatedOrders.findBy(year, productId);
    }

    @Override
    public Optional<NonFederatedOrder>findNonFederatedOrderBy(Year year, ProducerId producerId, ProductId productId)
    {
        return nonFederatedOrders.findBy(year, producerId,productId);
    }

    @Override
    public List<NonFederatedOrder>findNonFederatedOrdersBy(Year year, ProductId productId)
    {
        return nonFederatedOrders.findBy(year, productId);
    }

    @Override
    public void addSellOrder(SellOrder order)
    {
        sellOrderContainer.add(order);
    }

    @Override
    public List<SellOrder> findSellOrdersBy(Year year, ProductId productId)
    {
        return sellOrderContainer.findBy(year, productId);
    }
}
