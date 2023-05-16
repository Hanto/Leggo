package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProductEntity
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = ["uri"])
interface ProductMongo : MongoRepository<ProductEntity, String>
{
    fun queryByProductId(productId: String): ProductEntity?
}
