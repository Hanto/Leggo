package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.common.PricePerKilometer;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import com.leggo.cooperativa.domain.services.LogisticsService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PerishableLogistics implements LogisticsService
{
    private final BaseLogistics calc = BaseLogistics.builder()
        .smallLogisticPricePerKilogramAndKilometer(PricePerKilogramAndKilometer.of("0.01"))
        .smallLogisticMaxDistance(Kilometer.of(100))
        .biglogisticsMaxDistance(Kilometer.of(100))
        .bigLogisticPriceCut(0.5d)
        .biglogisticPricePerKilometer(PricePerKilometer.of("5.00"))
        .biglogisticTruckCapacity(Kilogram.of(1000f))
        .biglogisticUsesMultipleTrips(true).build();

    @Override
    public SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced order)
    {
        return calc.calculateLogistic(order);
    }
}
