package com.leggo.cooperativa.infrastructure.repositories.mongodb.entities

import java.math.BigDecimal
import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sellOrder")
class SellOrderEntity
(
    @Id
    val sellOrderId: String,
    val year: Int,
    val productId: String,
    val quantity: Double,
    val marketRateDay: LocalDate,
    val distance: Double,
    val pricePerKilogram: BigDecimal,
    val logisticsPrice: BigDecimal,
    val taxes: Double
)
