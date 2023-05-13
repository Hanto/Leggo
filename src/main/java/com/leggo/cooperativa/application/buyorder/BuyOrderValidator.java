package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.seller.FederatedOrder;
import com.leggo.cooperativa.domain.model.seller.NonFederatedOrder;
import com.leggo.cooperativa.domain.repositories.SellerRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.lang.String.format;

@AllArgsConstructor
public class BuyOrderValidator
{
    public static final int MAX_ORDERS_FOR_SMALLPRODUCERS = 5;
    private final SellerRepository sellerRepository;

    public void validateFederateOrder(FederatedOrder order)
    {
        if (isAlreadySoldByANonFederatedSeller(order.getYear(), order.getProduct()))
            throw new IllegalArgumentException(format("There is already a federated seller for this product: %s", order));

        if (hasBigSellers(order.getYear(), order.getProducers()))
            throw new IllegalArgumentException(format("One of the producers is a big seller %s", order));
    }

    public void validateNonFederateOrder(NonFederatedOrder order)
    {
        if (isAlreadySoldAsFederatedSeller(order.getYear(), order.getProduct(), order.getProducer()))
            throw new IllegalArgumentException(format("This seller is already selling this product as FederatedSeller: %s", order));

        if (isAlreadySoldAsNonFederatedSeller(order.getYear(), order.getProduct(), order.getProducer()))
            throw new IllegalArgumentException(format("this seller is already selling this product: %s", order));

        if (isBigSeller(order.getYear(), order.getProducer()) && isAlreadySellingMoreThanFiveProducts(order.getYear(), order.getProducer()))
            throw new IllegalArgumentException(format("this small seller is already selling more than 5 products: %s", order));

        if (order.getTotalHectares().isZero())
            throw new IllegalArgumentException(format("Cannot create an selling order when this producer has no harvest for this product: %s", order));
    }

    private boolean isAlreadySoldAsFederatedSeller(Year year, Product product, Producer producer)
    {
        return sellerRepository.findFederatedSellerBy(year, product.getProductId())
            .map(federatedSeller -> federatedSeller.containsTheProducer(producer))
            .orElse(false);
    }

    private boolean isAlreadySoldAsNonFederatedSeller(Year year, Product product, Producer producer)
    {
        return sellerRepository.findNonFederatedSellerBy(year, product.getProductId(), producer.getProducerId())
            .isPresent();
    }

    private boolean isAlreadySoldByANonFederatedSeller(Year year, Product product)
    {
        return sellerRepository.findFederatedSellerBy(year, product.getProductId())
            .isPresent();
    }

    private boolean isAlreadySellingMoreThanFiveProducts(Year year, Producer producer)
    {
        return sellerRepository.numberOfNonFederatedOrders(year, producer.getProducerId()) >= MAX_ORDERS_FOR_SMALLPRODUCERS;
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
}
