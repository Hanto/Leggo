package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.domain.model.common.Hectare
import com.leggo.cooperativa.domain.model.common.Year
import com.leggo.cooperativa.domain.model.producer.Field
import com.leggo.cooperativa.domain.model.producer.Producer
import com.leggo.cooperativa.domain.model.producer.ProducerId
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.repositories.ProducerRepository
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.FieldEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerLimitEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerLimitMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerMongo
import java.util.Optional

class ProducerMongoRepository
(
    private val producerMongo: ProducerMongo,
    private val producerLimitMongo: ProducerLimitMongo
): ProducerRepository
{
    override fun addProducer(producer: Producer)
    {
        producerMongo.save(producer.toEntity())
    }

    override fun findProducerById(producerId: ProducerId): Optional<Producer>
    {
        val domain = producerMongo.queryByProducerId(producerId = producerId.id)?.toDomain()
        return Optional.ofNullable(domain)
    }

    override fun updateProducer(producer: Producer)
    {
        producerMongo.save(producer.toEntity())
    }

    override fun setMaxHectaresForSmallProducer(year: Year, maxHectares: Hectare)
    {
        producerLimitMongo.save(ProducerLimitEntity(year.amount, maxHectares.amount))
    }

    override fun maxHectaresForSmallProducer(year: Year): Optional<Hectare>
    {
        val hectare = producerLimitMongo.queryByYear(year.amount)?.maxHectaresForSmallProducer?.let { Hectare.of(it) }
        return Optional.ofNullable(hectare)
    }

    // ADAPTERS
    //--------------------------------------------------------------------------------------------------------

    private fun Producer.toEntity(): ProducerEntity
    {
        val fieldsByYear = this.fieldsByYear
            .mapKeys { it.key.amount }
            .mapValues { list -> list.value.map { FieldEntity(it.productId.id, it.hectares.amount) } }

        return ProducerEntity(
            producerId = this.producerId.id,
            name = this.name,
            fieldsByYear = fieldsByYear
        )
    }

    private fun ProducerEntity.toDomain(): Producer
    {
        val fieldsByYear = this.fieldsByYear
            .mapKeys { Year.of(it.key) }
            .mapValues { list -> list.value.map { Field(ProductId(it.productId), Hectare.of(it.hectares)) } }

        return Producer(
            ProducerId(this.producerId),
            this.name,
            fieldsByYear)
    }
}
