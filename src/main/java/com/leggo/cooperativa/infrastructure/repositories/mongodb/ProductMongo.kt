package com.leggo.cooperativa.infrastructure.repositories.mongodb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = "uri")
public interface ProductMongo extends MongoRepository<ProductEntity, String>
{
    Optional<ProductEntity> queryByProductId(String productId);
}
