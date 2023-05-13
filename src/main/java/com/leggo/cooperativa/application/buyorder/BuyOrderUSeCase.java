package com.leggo.cooperativa.application.buyorder;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.seller.FederatedOrder;
import com.leggo.cooperativa.domain.model.seller.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.seller.BuyOrderId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.repositories.SellerRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
public class BuyOrderUSeCase
{
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BuyOrderValidator validator;

    public void createFederatedSeller(CreatedFederatedOrderCommand command)
    {
        Product product = retrieveProduct(command.getProductId());
        Set<Producer>producers = retrieveProducers(command.getProducersIds());

        FederatedOrder order = new FederatedOrder(
            new BuyOrderId(), command.getYear(), producers, product, LocalDateTime.now());

        validator.validateFederateOrder(order);
        sellerRepository.addFederatedSeller(order);
    }

    public void createNonFedaratedSeller(CreateNonFederatedOrderCommand command)
    {
        Product product = retrieveProduct(command.getProductId());
        Producer producer = retrieveProducer(command.getProducerId());

        NonFederatedOrder order = new NonFederatedOrder(
            new BuyOrderId(), command.getYear(), producer, product, LocalDateTime.now());

        validator.validateNonFederateOrder(order);
        sellerRepository.addNonFederatedSeller(order);
    }

    public void setHectareLimitFor(Year year, Hectare hectare)
    {
        sellerRepository.setMaxHectaresForSmallProducer(year, hectare);
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

    private Set<Producer> retrieveProducers(Collection<ProducerId>producerIds)
    {
        return producerIds.stream()
            .map(this::retrieveProducer)
            .collect(Collectors.toUnmodifiableSet());
    }

    private Producer retrieveProducer(ProducerId producerId)
    {
        Optional<Producer> maybeProducer = producerRepository.findProducerById(producerId);

        if (maybeProducer.isEmpty())
            throw new IllegalArgumentException(format("the producer: %s, doesnt exist", producerId));

        return maybeProducer.get();
    }

    private Product retrieveProduct(ProductId productId)
    {
        Optional<Product>maybeProduct = productRepository.findProductById(productId);

        if (maybeProduct.isEmpty())
            throw new IllegalArgumentException(format("the product: %s, doesnt exist", productId));

        return maybeProduct.get();
    }
}
