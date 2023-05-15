package com.leggo.cooperativa.domain.services.logistics

import com.leggo.cooperativa.domain.model.common.Kilogram
import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare
import com.leggo.cooperativa.domain.model.common.Kilometer
import com.leggo.cooperativa.domain.model.common.PricePerKilogram
import com.leggo.cooperativa.domain.model.common.Year
import com.leggo.cooperativa.domain.model.product.MarketRate
import com.leggo.cooperativa.domain.model.product.Product
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.model.product.ProductType.NOT_PERISHABLE
import com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced
import com.leggo.cooperativa.infrastructure.repositories.memory.InMemoryDatabase
import java.math.BigDecimal
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AllProducsLogisticsTest
{
    private val productRepository =
        InMemoryDatabase()
    private val nonPerishable = NonPerishableLogistics()
    private val perishable = PerishableLogistics()
    private val map = mapOf(
        NOT_PERISHABLE to nonPerishable,
        PERISHABLE to perishable)

    private val underTest = AllProductsLogistics(map, productRepository)

    @Test
    fun testNotPerishable()
    {
        val product = Product(
            ProductId("ACEITE"), "aceite",
            KilogramsPerHectare(2000.0), MarketRate(PricePerKilogram.of("0.4347826")),
            NOT_PERISHABLE)
        productRepository.addProduct(product)

        val input = SellOrderProductPriced.builder()
            .yearOfHarvest(Year(2023))
            .productId(ProductId("ACEITE"))
            .quantity(Kilogram(2000.0))
            .marketRateDay(LocalDate.now())
            .distance(Kilometer(180.0))
            .productPrice(PricePerKilogram.of("0.50"))
            .build()

        val result = underTest.calculateLogistic(input)

        assertThat(result.logisticsPrice.amount).isEqualByComparingTo(BigDecimal("2115"))
    }

    @Test
    fun testPerishable()
    {
        val product = Product(
            ProductId("ACEITE"), "aceite",
            KilogramsPerHectare(2000.0), MarketRate(PricePerKilogram.of("0.4347826")),
            PERISHABLE)

        productRepository.addProduct(product)

        val input = SellOrderProductPriced.builder()
            .yearOfHarvest(Year(2023))
            .productId(ProductId("ACEITE"))
            .quantity(Kilogram(2000.0))
            .marketRateDay(LocalDate.now())
            .distance(Kilometer(180.0))
            .productPrice(PricePerKilogram.of("0.50"))
            .build()

        val result = underTest.calculateLogistic(input)

        assertThat(result.logisticsPrice.amount).isEqualByComparingTo(BigDecimal("3100"))
    }
}
