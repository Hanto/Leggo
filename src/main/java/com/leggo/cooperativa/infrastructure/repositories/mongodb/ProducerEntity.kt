package com.leggo.cooperativa.infrastructure.repositories.mongodb

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "producer")
data class ProducerEntity
(
    @Id
    val producerId: String,
    val name: String,
    val fieldsByYear: Map<Int, List<FieldEntity>>
)

data class FieldEntity
(
    val productId: String,
    val hectares: Double,
)

class ProducerLimitEntity
(
    @Id
    val year: Int,
    val maxHectaresForSmallProducer: Double
)
