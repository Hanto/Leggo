package com.leggo.cooperativa.infrastructure.repositories.mongodb.entities

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "federatedOrder")
data class FederatedOrderEntity
(
    @Id
    val buyOrderId: String,
    val year: Int,
    val contributors: List<ContributionEntity>,
    val productId: String,
    val soldTime: LocalDateTime
)

data class ContributionEntity
(
    val producerId: String,
    val kilograms: Double,
)
