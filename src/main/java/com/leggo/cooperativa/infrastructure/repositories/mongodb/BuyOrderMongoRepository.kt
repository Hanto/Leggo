package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId
import com.leggo.cooperativa.domain.model.buyorder.Contribution
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder
import com.leggo.cooperativa.domain.model.common.Kilogram
import com.leggo.cooperativa.domain.model.common.Year
import com.leggo.cooperativa.domain.model.producer.ProducerId
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.repositories.BuyOrderRepository
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ContributionEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.FederatedOrderEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.NonFederatedOrderEntity
import java.util.Optional

class BuyOrderMongoRepository
(
    private val federatedOrderMongo: FederatedOrderMongo,
    private val nonFederatedOrderMongo: NonFederatedOrderMongo,
) : BuyOrderRepository
{
    override fun addFederatedOrder(order: FederatedOrder)
    {
        federatedOrderMongo.save(order.toEntity())
    }

    override fun findFederatedOrderBy(year: Year, productId: ProductId): Optional<FederatedOrder>
    {
        val federatedOrder = federatedOrderMongo.queryByYearAndProductId(year.amount, productId.id)?.toDomain()
        return Optional.ofNullable(federatedOrder)
    }

    override fun addNonFederatedOrder(order: NonFederatedOrder)
    {
        nonFederatedOrderMongo.save(order.toEntity())
    }

    override fun findNonFederatedOrderBy(year: Year, product: ProductId, producerId: ProducerId): Optional<NonFederatedOrder>
    {
        val nonFederatedOrder = nonFederatedOrderMongo.queryByYearAndProducIdAndContributionProducerId(year.amount, product.id, producerId.id)?.toDomain()
        return Optional.ofNullable(nonFederatedOrder)
    }

    override fun findNonFederatedOrderBy(year: Year, producerId: ProducerId, productId: ProductId): Optional<NonFederatedOrder>
    {
        TODO("Not yet implemented")
    }

    override fun numberOfNonFederatedOrdersFrom(year: Year, producerId: ProducerId): Int
    {
        TODO("Not yet implemented")
    }

    override fun findNonFederatedOrdersBy(year: Year, productId: ProductId): List<NonFederatedOrder>
    {
        TODO("Not yet implemented")
    }

    // ADAPTERS
    //--------------------------------------------------------------------------------------------------------

    private fun FederatedOrder.toEntity(): FederatedOrderEntity
    {
        val contributors = this.contributors
            .map { ContributionEntity(producerId = it.producerId.id, kilograms = it.kilograms.amount) }

        return FederatedOrderEntity(
            buyOrderId = this.buyOrderId.id.toString(),
            year = this.year.amount,
            contributors = contributors,
            productId = this.productId.id,
            soldTime = this.soldTime
        )
    }

    private fun FederatedOrderEntity.toDomain(): FederatedOrder
    {
        val contributors = this.contributors
            .map { Contribution(ProducerId(it.producerId), Kilogram.of(it.kilograms)) }
            .toSet()

        return FederatedOrder(
            BuyOrderId(this.buyOrderId),
            Year.of(this.year),
            contributors,
            ProductId(this.productId),
            this.soldTime)
    }

    private fun NonFederatedOrder.toEntity(): NonFederatedOrderEntity =
        NonFederatedOrderEntity(
            buyOrderId = this.buyOrderId.id.toString(),
            year = this.year.amount,
            contribution = ContributionEntity(producerId = this.contributor.producerId.id, kilograms = this.contributor.kilograms.amount),
            producId = this.productId.id,
            soldTime = this.soldTime)

    private fun NonFederatedOrderEntity.toDomain(): NonFederatedOrder =
        NonFederatedOrder(
            BuyOrderId(this.buyOrderId),
            Year.of(this.year),
            Contribution(ProducerId(this.contribution.producerId), Kilogram.of(this.contribution.kilograms)),
            ProductId(this.producId),
            this.soldTime)
}
