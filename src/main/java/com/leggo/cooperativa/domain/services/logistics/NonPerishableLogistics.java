package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.common.PricePerKilometer;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;

public class NonPerishableLogistics implements LogisticCalculatorService
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
    public SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced order)
    {
        return calc.calculateLogistic(order);
    }
}
