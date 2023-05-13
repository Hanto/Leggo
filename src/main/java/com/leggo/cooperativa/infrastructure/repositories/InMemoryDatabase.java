package com.leggo.cooperativa.infrastructure.repositories;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.seller.FederatedOrder;
import com.leggo.cooperativa.domain.model.seller.NonFederatedOrder;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.SellerRepository;

import java.util.HashMap;
import java.util.Map;
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
    public Optional<FederatedOrder> findFederatedSellerBy(Year year, ProductId productId)
    {
        return federatedSellers.findBy(year, productId);
    }

    @Override
    public void addNonFederatedSeller(NonFederatedOrder seller)
    {
       nonFederatedSellers.add(seller);
    }

    @Override
    public Optional<NonFederatedOrder> findNonFederatedSellerBy(Year year, ProductId productId, ProducerId producerId)
    {
        return nonFederatedSellers.findBy(year, producerId, productId);
    }

    @Override
    public int numberOfNonFederatedOrders(Year year, ProducerId producerId)
    {
        return nonFederatedSellers.findBy(year, producerId).size();
    }
}
