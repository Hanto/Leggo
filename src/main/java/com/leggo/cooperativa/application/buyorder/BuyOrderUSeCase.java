package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Kilograms;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.SellerRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
public class BuyOrderUSeCase
{
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final SellerRepository sellerRepository;
    private final BuyOrderValidator validator;

    public void createFederatedOrder(CreatedFederatedOrderCommand command)
    {
        Set<Producer>producers = retrieveProducers(command.getProducersIds());
        Product product = retriveProduct(command.getProductId());
        Kilograms kilograms = getTotalKilograms(producers, command.getYear(), product);

        FederatedOrder order = new FederatedOrder(
            new BuyOrderId(), command.getYear(), command.getProducersIds(), command.getProductId(), LocalDateTime.now(), kilograms);

        validator.validateFederateOrder(order);
        sellerRepository.addFederatedSeller(order);
    }

    public void createNonFederatedOrder(CreateNonFederatedOrderCommand command)
    {
        Producer producer = retrieveProducer(command.getProducerId());
        Product product = retriveProduct(command.getProductId());
        Kilograms kilograms = getTotalKilograms(producer, command.getYear(), product);

        NonFederatedOrder order = new NonFederatedOrder(
            new BuyOrderId(), command.getYear(), command.getProducerId(), command.getProductId(), LocalDateTime.now(), kilograms);

        validator.validateNonFederateOrder(order);
        sellerRepository.addNonFederatedSeller(order);
    }

    public void setHectareLimitFor(Year year, Hectare hectare)
    {
        sellerRepository.setMaxHectaresForSmallProducer(year, hectare);
    }

    // KILOGRAMS
    //--------------------------------------------------------------------------------------------------------

    private Kilograms getTotalKilograms(Collection<Producer>producers, Year year, Product product)
    {
        Hectare hectares = producers.stream()
            .map(producer -> producer.getTotalHectaresFor(year, product.getProductId()))
            .reduce(Hectare.ofZero(), Hectare::sum);

        return new Kilograms(hectares.getAmount() * product.getGetTonsPerHectare());
    }

    public Kilograms getTotalKilograms(Producer producer, Year year, Product product)
    {
        Hectare hectares = producer.getTotalHectaresFor(year, product.getProductId());

        return new Kilograms(hectares.getAmount() * product.getGetTonsPerHectare());
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

    private Product retriveProduct(ProductId productId)
    {
        return productRepository.findProductById(productId)
            .orElseThrow(() -> new IllegalArgumentException(format("the product: %s, doesnt exist", productId)) );
    }

    private Producer retrieveProducer(ProducerId producerId)
    {
        return producerRepository.findProducerById(producerId)
            .orElseThrow(() -> new IllegalArgumentException(format("the producer: %s, doesnt exist", producerId)) );
    }

    private Set<Producer> retrieveProducers(Collection<ProducerId>producerIds)
    {
        return producerIds.stream()
            .map(this::retrieveProducer)
            .collect(Collectors.toUnmodifiableSet());
    }
}
