package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilometer;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class BaseLogisticCalculator implements LogisticCalculatorService
{
    private final PricePerKilogramAndKilometer smallLogisticPricePerKilogramAndKilometer;
    private final Kilometer smallLogisticMaxDistance;
    private final Kilometer biglogisticsMaxDistance;
    private final Double bigLogisticPriceCut;
    private final PricePerKilometer biglogisticPricePerKilometer;
    private final Kilogram biglogisticTruckCapacity;
    private final boolean biglogisticUsesMultipleTrips;

    // MAIN
    //--------------------------------------------------------------------------------------------------------

    @Override
    public SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced order)
    {
        Kilometer distanceForSmallLogistics = getDistanceForSmallLogistics(order.getDistance());
        Kilometer distanceForBigLogistics = getDistanceForBigLogistics(order.getDistance());

        Price bigLogisticPrice = bigLogisticCost(biglogisticPricePerKilometer, distanceForBigLogistics, order);
        Price smallLogisticPrice = smallLogisticCost(smallLogisticPricePerKilogramAndKilometer, distanceForSmallLogistics, order);
        Price logisticsPrice = bigLogisticPrice.add(smallLogisticPrice);

        return SellOrderLogisticPriced.builder()
            .yearOfHarvest(order.getYearOfHarvest())
            .productId(order.getProductId())
            .quantity(order.getQuantity())
            .marketRateDay(order.getMarketRateDay())
            .distance(order.getDistance())
            .productPrice(order.getProductPrice())
            .logisticsPrice(logisticsPrice)
            .build();
    }

    // LOGISTIC TYPES
    //--------------------------------------------------------------------------------------------------------

    private Price bigLogisticCost(PricePerKilometer pricePerKilometer, Kilometer distance, SellOrderProductPriced order)
    {
        List<Kilogram> trucks = fillUntilQuantity(order.getQuantity(), biglogisticTruckCapacity);

        return trucks.stream()
            .map(weight -> bigLogisticCostPerTruck(pricePerKilometer, distance, weight, order))
            .reduce(Price.of("0"), Price::add);
    }

    private Price bigLogisticCostPerTruck(PricePerKilometer pricePerKilometer, Kilometer distance, Kilogram quantity, SellOrderProductPriced order)
    {
        double numberOfTrips = biglogisticUsesMultipleTrips ? distance.divide(biglogisticsMaxDistance) : 1d;

        Price pricePerTrip = order.getProductPrice().multiply(quantity).multiply(bigLogisticPriceCut);
        Price pricePerAllTrips = pricePerTrip.multiply(numberOfTrips);
        Price priceForDistance = pricePerKilometer.multiply(distance);

        return pricePerAllTrips.add(priceForDistance);
    }

    private Price smallLogisticCost(PricePerKilogramAndKilometer pricePerKilogramAndKilometer, Kilometer distance, SellOrderProductPriced order)
    {
        return pricePerKilogramAndKilometer.multiply(order.getQuantity(), distance);
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

    private Kilometer getDistanceForBigLogistics(Kilometer distance)
    {
        Kilometer bigLogisticDistance = distance.minus(getDistanceForSmallLogistics(distance));
        return bigLogisticDistance.isZeroOrLess() ? Kilometer.ofZero() : bigLogisticDistance;
    }

    private Kilometer getDistanceForSmallLogistics(Kilometer distance)
    {
        return distance.modulus(smallLogisticMaxDistance);
    }

    private List<Kilogram> fillUntilQuantity(Kilogram quantity, Kilogram maxQuantityPerSlot)
    {
        int numberOfFullSplits = (int)Math.floor(quantity.divide(maxQuantityPerSlot));
        Kilogram lastSplit = quantity.modulus(maxQuantityPerSlot);

        List<Kilogram> list = Stream.generate(() -> maxQuantityPerSlot)
            .limit(numberOfFullSplits)
            .collect(Collectors.toList());

        if (!lastSplit.isZeroOrLess())
            list.add(lastSplit);

        return list;
    }
}
