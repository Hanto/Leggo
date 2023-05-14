package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilometer;
import com.leggo.cooperativa.domain.model.product.Product;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class PerishableLogistics implements LogisticCalculator
{
    private final BaseLogisticCalculator calc = BaseLogisticCalculator.builder()
        .smallLogisticPricePerKilogramAndKilometer(PricePerKilogramAndKilometer.of("0.01"))
        .smallLogisticMaxDistance(Kilometer.of(100))
        .biglogisticsMaxDistance(Kilometer.of(100))
        .bigLogisticPriceCut(0.5d)
        .biglogisticPricePerKilometer(PricePerKilometer.of("5.00"))
        .biglogisticTruckCapacity(Kilogram.of(1000f))
        .biglogisticUsesMultipleTrips(true).build();

    @Override
    public Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day)
    {
        return calc.calculateLogistic(product, distance, quantity, day);
    }
}
