package com.leggo.cooperativa.infrastructure.repositories.mongodb.entities

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = ["uri"])
interface ProducerLimitEntityMongo : MongoRepository<ProducerLimitEntity, Int>
{
    fun queryByYear(year: Int): ProducerLimitEntity?
}
