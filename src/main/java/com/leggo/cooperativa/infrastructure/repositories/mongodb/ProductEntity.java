package com.leggo.cooperativa.infrastructure.repositories.mongodb;

import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Document(collection = "product") @Builder @ToString
public class ProductEntity
{
    @Id
    private String productId;
    private String name;
    private double kilogramsPerHectare;
    private MarketRateEntity marketRates;

    @Builder @ToString
    public static class MarketRateEntity
    {
        private Map<LocalDate, BigDecimal> pricesByDay;
        private BigDecimal initialPricePerKilogram;
    }
}
