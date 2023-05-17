package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor @ToString @Getter
public class MarketRate
{
    private final TreeMap<LocalDate, PricePerKilogram> pricesByDay;
    private final PricePerKilogram initialPricePerKilogram;

    public MarketRate(PricePerKilogram initialPricePerKilogram)
    {
        this.initialPricePerKilogram = initialPricePerKilogram;
        this.pricesByDay = new TreeMap<>();
    }

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
