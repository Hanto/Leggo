package com.leggo.cooperativa.infrastructure.repositories.mongodb

import java.math.BigDecimal
import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product")
data class ProductEntity
(
    @Id
    val productId: String,
    val name: String,
    val kilogramsPerHectare: Double,
    val marketRates: MarketRateEntity,
    val productType: String
)

data class MarketRateEntity
(
    val pricesByDay: Map<LocalDate, BigDecimal>,
    val initialPricePerKilogram: BigDecimal,
)
