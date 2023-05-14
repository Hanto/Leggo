package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.product.Product;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
public class AllProductsLogisticCalculator implements LogisticCalculator
{
    private final Map<Class<? extends Product>, LogisticCalculator>calculators;

    @Override
    public Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day)
    {
        LogisticCalculator productCalculator = calculators.get(product.getClass());

        if (productCalculator == null)
            throw new RuntimeException("There is not a logistic calculator for this kind of product");

        return productCalculator.calculateLogistic(product, distance, quantity, day);
    }
}
