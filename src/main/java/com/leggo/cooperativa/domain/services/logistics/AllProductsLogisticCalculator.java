package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class AllProductsLogisticCalculator implements LogisticCalculatorService
{
    private final Map<Class<? extends Product>, LogisticCalculatorService>calculators;
    private final ProductRepository productRepository;

    @Override
    public SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced order)
    {
        Product product = productRepository.findProductById(order.getProductId())
            .orElseThrow( ()-> new RuntimeException("Product doesnt exist"));

        LogisticCalculatorService productCalculator = calculators.get(product.getClass());

        if (productCalculator == null)
            throw new RuntimeException("There is not a logistic calculator for this kind of product");

        return productCalculator.calculateLogistic(order);
    }
}
