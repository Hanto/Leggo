package com.leggo.cooperativa.infrastructure.repositories.mongodb.entities

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = ["uri"])
interface NonFederatedOrderMongo : MongoRepository<NonFederatedOrderEntity, String>
{
    fun queryByYearAndProductIdAndContributionProducerId(year: Int, productId: String, producerId: String): NonFederatedOrderEntity?
    fun countByYearAndContributionProducerId(year: Int, producerId: String): Int
    fun queryByYearAndProductId(year: Int, productId: String): List<NonFederatedOrderEntity>
}
