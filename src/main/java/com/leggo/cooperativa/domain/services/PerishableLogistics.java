package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilogramAndKilometer;
import com.leggo.cooperativa.domain.model.logistics.PricePerKilometer;
import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.Product;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class PerishableLogistics implements LogisticCalculator
{
    private final PricePerKilogramAndKilometer smallLogisticPricePerKilogramAndKilometer = PricePerKilogramAndKilometer.of("0.01");
    private final Kilometer smallLogisticMaxDistance = Kilometer.of(100);
    private final Kilometer biglogisticsMaxDistance = Kilometer.of(50);
    private final Double bigLogisticPriceCut = 0.5d;
    private final PricePerKilometer biglogisticPricePerKilometer = PricePerKilometer.of("0.05");
    private final Kilogram biglogisticTruckCapacity = Kilogram.of(1000f);
    private final boolean biglogisticUsesMultipleTrips = false;

    // MAIN
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day)
    {
        Kilometer distanceForSmallLogistics = getDistanceForSmallLogistics(distance);
        Kilometer distanceForBigLogistics = getDistanceForBigLogistics(distance);

        PricePerKilogram pricePerKilogram = product.lastMarketPrice(day);
        Price bigLogisticPrice =bigLogisticCost(pricePerKilogram, biglogisticPricePerKilometer, quantity, distanceForBigLogistics);
        Price smallLogisticPrice = smallLogisticCost(smallLogisticPricePerKilogramAndKilometer, quantity, distanceForSmallLogistics);

        return bigLogisticPrice.add(smallLogisticPrice);
    }

    // LOGISTIC TYPES
    //--------------------------------------------------------------------------------------------------------

    private Price bigLogisticCost(PricePerKilogram pricePerKilogram, PricePerKilometer pricePerKilometer, Kilogram quantity, Kilometer distance)
    {
        List<Kilogram>trucks = fillUntilQuantity(quantity, biglogisticTruckCapacity);

        return trucks.stream()
            .map(weight -> bigLogisticCostPerTruck(pricePerKilogram, pricePerKilometer, weight, distance))
            .reduce(Price.of("0"), Price::add);
    }

    private Price bigLogisticCostPerTruck(PricePerKilogram pricePerKilogram, PricePerKilometer pricePerKilometer, Kilogram quantity, Kilometer distance)
    {
        double numberOfTrips = biglogisticUsesMultipleTrips ? distance.divide(biglogisticsMaxDistance) : 1d;

        Price priceForQuantity = pricePerKilogram.multiply(quantity).multiply(bigLogisticPriceCut).multiply(numberOfTrips);
        Price priceForDistance = pricePerKilometer.multiply(distance);

        return priceForQuantity.add(priceForDistance);
    }

    private Price smallLogisticCost(PricePerKilogramAndKilometer pricePerKilogramAndKilometer, Kilogram quantity, Kilometer distance)
    {
        return pricePerKilogramAndKilometer.multiply(quantity, distance);
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
