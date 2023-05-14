package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.SellerRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
public class BuyOrderValidator
{
    public static final int MAX_ORDERS_FOR_SMALL_PRODUCERS = 5;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProducerRepository producerRepository;

    public void validateFederateOrder(FederatedOrder order)
    {
        Set<Producer>producers = retrieveProducers(order.getProducerIds());

        if (productDoesntExist(order.getProductId()))
            throw new IllegalArgumentException(format("the product doesnt exist: %s", order));

        if (isAlreadySoldByANonFederatedSeller(order.getYear(), order.getProductId()))
            throw new IllegalArgumentException(format("There is already a federated seller for this product: %s", order));

        if (hasBigSellers(order.getYear(), producers))
            throw new IllegalArgumentException(format("One of the producers is a big seller %s", order));

        if (order.getKilograms().isZeroOrLess())
            throw new IllegalArgumentException(format("Cannot create an order when this producer has no harvest for this product: %s", order));
    }

    public void validateNonFederateOrder(NonFederatedOrder order)
    {
        Producer producer = retrieveProducer(order.getProducerId());

        if (productDoesntExist(order.getProductId()))
            throw new IllegalArgumentException(format("the product doesnt exist: %s", order));

        if (isAlreadySoldAsFederatedSeller(order.getYear(), order.getProductId(), order.getProducerId()))
            throw new IllegalArgumentException(format("This seller is already selling this product as FederatedSeller: %s", order));

        if (isAlreadySoldAsNonFederatedSeller(order.getYear(), order.getProductId(), order.getProducerId()))
            throw new IllegalArgumentException(format("this seller is already selling this product: %s", order));

        if (isBigSeller(order.getYear(), producer) && isAlreadySellingMoreThanFiveProducts(order.getYear(), order.getProducerId()))
            throw new IllegalArgumentException(format("this small seller is already selling more than 5 products: %s", order));

        if (order.getKilograms().isZeroOrLess())
            throw new IllegalArgumentException(format("Cannot create an order when this producer has no harvest for this product: %s", order));
    }

    // VALIDATORS
    //--------------------------------------------------------------------------------------------------------

    private boolean productDoesntExist(ProductId productId)
    {
        return productRepository.findProductById(productId)
            .isEmpty();
    }

    private boolean isAlreadySoldAsFederatedSeller(Year year, ProductId productId, ProducerId producer)
    {
        return sellerRepository.findFederatedSellerBy(year, productId)
            .map(federatedSeller -> federatedSeller.containsTheProducer(producer))
            .orElse(false);
    }

    private boolean isAlreadySoldAsNonFederatedSeller(Year year, ProductId productId, ProducerId producerId)
    {
        return sellerRepository.findNonFederatedSellerBy(year, productId, producerId)
            .isPresent();
    }

    private boolean isAlreadySoldByANonFederatedSeller(Year year, ProductId productId)
    {
        return sellerRepository.findFederatedSellerBy(year, productId)
            .isPresent();
    }

    private boolean isAlreadySellingMoreThanFiveProducts(Year year, ProducerId producerId)
    {
        return sellerRepository.numberOfNonFederatedOrders(year, producerId) >= MAX_ORDERS_FOR_SMALL_PRODUCERS;
    }

    private boolean hasBigSellers(Year year, Collection<Producer> producers)
    {
        Optional<Hectare> maybeHectareLimit = sellerRepository.maxHectaresForSmallProducer(year);

        if (maybeHectareLimit.isEmpty())
            throw new IllegalArgumentException(format("The are no hectare limits for year %s", year));

        Hectare hectareLimit = maybeHectareLimit.get();

        return producers.stream()
            .anyMatch(producer -> producer.getTotalHectaresFor(year).isGreater(hectareLimit) );
    }

    private boolean isBigSeller(Year year, Producer producer)
    {
        return hasBigSellers(year, Collections.singletonList(producer));
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
