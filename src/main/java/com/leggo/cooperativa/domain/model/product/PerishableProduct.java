package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.domain.model.common.Kilograms;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;

import java.time.LocalDate;

public class PerishableProduct extends Product
{
    private static final int SMALL_LOGISTIC_MAX_DISTANCE = 100;
    private static final float LONG_LOGISTIC_PRICE_INCREASE = 0.5f;

    public PerishableProduct(ProductId productId, String name, float productionPerHectare, MarketRate marketRates)
    {
        super(productId, name, productionPerHectare, marketRates);
    }

    public Price calculateLogistic(Kilometer distance, Kilograms quantity, LocalDate day)
    {
        Kilometer distanceForSmallLogistics = getDistanceForSmallLogistics(distance);
        Kilometer distanceForBigLogistics = getDistanceForBigLogistics(distance);

        PricePerKilogram longDistancePricePerKilogram = calculateHighDistance(distanceForBigLogistics, quantity, day);

        return Price.of("0");
    }

    private PricePerKilogram calculateHighDistance(Kilometer distance, Kilograms quantity, LocalDate day)
    {
        if (distance.isZero())
            return PricePerKilogram.ofZero();

        int numberOfTrucks = (int)Math.ceil(quantity.getAmount());
        PricePerKilogram productPricePerKilogram = lastMarketPrice(day);

        return productPricePerKilogram.multiply(numberOfTrucks * LONG_LOGISTIC_PRICE_INCREASE);
    }

    private PricePerKilogram calculateShortDistance(Kilometer distance)
    {
        return PricePerKilogram.of("0.1");
    }

    // LOGISTIC TYPES
    //--------------------------------------------------------------------------------------------------------

    private static Kilometer getDistanceForBigLogistics(Kilometer distance)
    {
        float bigLogisticDistance = distance.amount - getDistanceForSmallLogistics(distance).amount;
        return Kilometer.of(Math.max(bigLogisticDistance, 0));
    }

    private static Kilometer getDistanceForSmallLogistics(Kilometer distance)
    {
        return Kilometer.of(distance.amount % SMALL_LOGISTIC_MAX_DISTANCE);
    }
}
