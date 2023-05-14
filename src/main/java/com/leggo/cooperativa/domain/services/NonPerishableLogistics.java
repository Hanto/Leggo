package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilometer;
import com.leggo.cooperativa.domain.model.product.Product;

import java.time.LocalDate;

public class NonPerishableLogistics implements LogisticCalculator
{
    private final BaseLogisticCalculator calc = BaseLogisticCalculator.builder()
        .smallLogisticPricePerKilogramAndKilometer(PricePerKilogramAndKilometer.of("0.01"))
        .smallLogisticMaxDistance(Kilometer.of(50))
        .biglogisticsMaxDistance(Kilometer.of(50))
        .bigLogisticPriceCut(0.5d)
        .biglogisticPricePerKilometer(PricePerKilometer.of("0.05"))
        .biglogisticTruckCapacity(Kilogram.of(1000f))
        .biglogisticUsesMultipleTrips(true).build();

    @Override
    public Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day)
    {
        return calc.calculateLogistic(product, distance, quantity, day);
    }
}
