package com.leggo.cooperativa.domain.model.product;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor @ToString
public class MarketRate
{
    private final TreeMap<LocalDate, PricePerKilogram> pricesByDay = new TreeMap<>();
    private final PricePerKilogram initialPricePerKilogram;

    public void addOrReplacePrice(PricePerKilogram pricePerKilogram, LocalDate day)
    {
        pricesByDay.put(day, pricePerKilogram);
    }

    public PricePerKilogram lastPriceFor(LocalDate day)
    {
        Map.Entry<LocalDate, PricePerKilogram> dailyPriceEntry = pricesByDay.floorEntry(day);

        if (dailyPriceEntry == null)
            return initialPricePerKilogram;

        return dailyPriceEntry.getValue();
    }
}
