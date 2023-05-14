package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.product.Product;

import java.time.LocalDate;

public interface LogisticCalculator
{
    Price calculateLogistic(Product product, Kilometer distance, Kilogram quantity, LocalDate day);
}
