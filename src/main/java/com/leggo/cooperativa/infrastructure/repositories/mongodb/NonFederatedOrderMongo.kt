package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.NonFederatedOrderEntity
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = ["uri"])
interface NonFederatedOrderMongo : MongoRepository<NonFederatedOrderEntity, String>
{
    fun queryByYearAndProducIdAndContributionProducerId(year: Int, productId: String, producerId: String): NonFederatedOrderEntity?
}
