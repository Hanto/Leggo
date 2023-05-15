package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import com.leggo.cooperativa.domain.services.LogisticsService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class AllProductsLogistics implements LogisticsService
{
    private final Map<ProductType, LogisticsService>calculators;
    private final ProductRepository productRepository;

    @Override
    public SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced order)
    {
        Product product = productRepository.findProductById(order.getProductId())
            .orElseThrow( ()-> new RuntimeException("Product doesnt exist"));

        LogisticsService productCalculator = calculators.get(product.getProductType());

        if (productCalculator == null)
            throw new RuntimeException("There is not a logistic calculator for this kind of product");

        return productCalculator.calculateLogistic(order);
    }
}
