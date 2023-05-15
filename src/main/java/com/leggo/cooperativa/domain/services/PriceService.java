package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderDemanded;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PriceService
{
    private final ProductRepository productRepository;

    public SellOrderProductPriced price(SellOrderDemanded order)
    {
        Product product = productRepository.findProductById(order.getProductId())
            .orElseThrow( ()->new IllegalArgumentException("Product doesn't exist") );

        PricePerKilogram basePrice = product.lastMarketPrice(order.getMarketRateDay());
        PricePerKilogram priceWithMargin = applyMargin(basePrice);

        return buildProducedPriced(order, priceWithMargin);
    }

    private static final double MARGIN = 1.15d;
    private PricePerKilogram applyMargin(PricePerKilogram basePrice)
    {
        return basePrice.multiply(MARGIN);
    }

    private static SellOrderProductPriced buildProducedPriced(SellOrderDemanded order, PricePerKilogram priceWithMargin)
    {
        return SellOrderProductPriced.builder()
            .yearOfHarvest(order.getYearOfHarvest())
            .productId(order.getProductId())
            .quantity(order.getTotalKilograms())
            .marketRateDay(order.getMarketRateDay())
            .distance(order.getDistance())
            .productPrice(priceWithMargin)
            .build();
    }
}
