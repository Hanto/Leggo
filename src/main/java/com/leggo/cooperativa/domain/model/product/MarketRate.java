package com.leggo.cooperativa.domain.model.product;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
public class MarketRate
{
    private final TreeMap<LocalDate, Price> pricesByDay = new TreeMap<>();
    private final Price initialPrice;

    public void addOrReplacePrice(Price price, LocalDate day)
    {
        pricesByDay.put(day, price);
    }

    public Price lastPriceFor(LocalDate day)
    {
        Map.Entry<LocalDate, Price> dailyPriceEntry = pricesByDay.floorEntry(day);

        if (dailyPriceEntry == null)
            return initialPrice;

        return dailyPriceEntry.getValue();
    }
}
