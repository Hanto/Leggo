package com.leggo.cooperativa.application.logistics;

import com.leggo.cooperativa.domain.model.common.Kilograms;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.product.Product;

import java.time.LocalDate;

public class LogisticsUseCase
{
    public Price calculatePriceFor(Product product, Kilometer distance, Kilograms quantity, LocalDate day)
    {
        return null;
    }
}
