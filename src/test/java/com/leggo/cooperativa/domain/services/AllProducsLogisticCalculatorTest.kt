package com.leggo.cooperativa.domain.services

import com.leggo.cooperativa.domain.model.common.Kilogram
import com.leggo.cooperativa.domain.model.common.Kilometer
import com.leggo.cooperativa.domain.model.product.NonPerishableProduct
import com.leggo.cooperativa.domain.model.product.PerishableProduct
import com.leggo.cooperativa.domain.model.product.PricePerKilogram
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.model.product.ProductType.NOT_PERISHABLE
import com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE
import java.math.BigDecimal
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AllProducsLogisticCalculatorTest
{
    private val nonPerishable = NonPerishableLogistics();
    private val perishable = PerishableLogistics();

    private val underTest = AllProductsLogisticCalculator(mapOf(
        NonPerishableProduct::class.java to nonPerishable,
        PerishableProduct::class.java to perishable))

    @Test
    fun testNotPerishable()
    {
        val product = NOT_PERISHABLE.createProduct(
            ProductId("ACEITE"), "aceite", 2000f, PricePerKilogram.of("0.50"))

        val price = underTest.calculateLogistic(
            product, Kilometer.of(180.0), Kilogram.of(2000.0), LocalDate.now())

        assertThat(price.amount).isEqualByComparingTo(BigDecimal("2115"))
    }

    @Test
    fun testPerishable()
    {
        val product = PERISHABLE.createProduct(
            ProductId("ACEITE"), "aceite", 2000f, PricePerKilogram.of("0.50"))

        val price = underTest.calculateLogistic(
            product, Kilometer.of(180.0), Kilogram.of(2000.0), LocalDate.now())

        assertThat(price.amount).isEqualByComparingTo(BigDecimal("2110"))
    }
}
