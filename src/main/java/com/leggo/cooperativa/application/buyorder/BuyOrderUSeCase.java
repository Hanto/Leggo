package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
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
    private final ProducerRepository producerRepository;
    private final SellerRepository sellerRepository;
    private final BuyOrderValidator validator;

    public void createFederatedOrder(CreatedFederatedOrderCommand command)
    {
        Hectare hectares = getTotalHectares(command.getProducersIds(), command.getYear(), command.getProductId());

        FederatedOrder order = new FederatedOrder(
            new BuyOrderId(), command.getYear(), command.getProducersIds(), command.getProductId(), LocalDateTime.now(), hectares);

        validator.validateFederateOrder(order);
        sellerRepository.addFederatedSeller(order);
    }

    public void createNonFederatedOrder(CreateNonFederatedOrderCommand command)
    {
        Hectare hectares = getTotalHectares(command.getProducerId(), command.getYear(), command.getProductId());

        NonFederatedOrder order = new NonFederatedOrder(
            new BuyOrderId(), command.getYear(), command.getProducerId(), command.getProductId(), LocalDateTime.now(), hectares);

        validator.validateNonFederateOrder(order);
        sellerRepository.addNonFederatedSeller(order);
    }

    public void setHectareLimitFor(Year year, Hectare hectare)
    {
        sellerRepository.setMaxHectaresForSmallProducer(year, hectare);
    }

    // HECTARES
    //--------------------------------------------------------------------------------------------------------

    private Hectare getTotalHectares(Collection<ProducerId>producersIds, Year year, ProductId productId)
    {
        Set<Producer>producers = retrieveProducers(producersIds);

        return producers.stream()
            .map(producer -> producer.getTotalHectaresFor(year, productId))
            .reduce(Hectare.ofZero(), Hectare::sum);
    }

    public Hectare getTotalHectares(ProducerId producerId, Year year, ProductId productId)
    {
        Producer producer = retrieveProducer(producerId);

        return producer.getTotalHectaresFor(year, productId);
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

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
