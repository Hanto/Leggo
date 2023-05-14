package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilometer;
import com.leggo.cooperativa.domain.model.product.Product;

import java.time.LocalDate;

public class NonPerishableLogisticsCalculator implements LogisticCalculator
{
    private final BaseLogisticCalculator calc = new BaseLogisticCalculator(
        PricePerKilogramAndKilometer.of("0.01"),
        Kilometer.of(50),
        Kilometer.of(50),
        0.5d,
        PricePerKilometer.of("0.05"),
        Kilogram.of(1000f),
        true);

    @Override
    public Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day)
    {
        return calc.calculateLogistic(product, distance, quantity, day);
    }
}
