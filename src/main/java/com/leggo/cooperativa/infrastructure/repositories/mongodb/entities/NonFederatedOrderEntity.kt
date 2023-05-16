package com.leggo.cooperativa.infrastructure.repositories.mongodb.entities

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "nonFederatedOrder")
class NonFederatedOrderEntity
(
    @Id
    val buyOrderId: String,
    val year: Int,
    val contribution: ContributionEntity,
    val productId: String,
    val soldTime: LocalDateTime
)
