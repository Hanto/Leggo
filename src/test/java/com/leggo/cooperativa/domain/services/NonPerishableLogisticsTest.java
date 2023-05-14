package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.leggo.cooperativa.domain.model.product.ProductType.NOT_PERISHABLE;
import static org.assertj.core.api.Assertions.assertThat;

class NonPerishableLogisticsTest
{
    NonPerishableLogisticsCalculator underTest = new NonPerishableLogisticsCalculator();

    @Test
    public void exampleTest()
    {
        Product product = NOT_PERISHABLE.createProduct(
            new ProductId("ACEITE"), "aceite", 2000f, PricePerKilogram.of("0.50"));

        Price price = underTest.calculateLogistic(
            product, Kilometer.of(180f), Kilogram.of(2000f), LocalDate.now());

        assertThat(price.getAmount().compareTo(new BigDecimal("2115"))).isEqualTo(0);
    }
}
