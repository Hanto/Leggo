package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.domain.model.common.Kilogram
import com.leggo.cooperativa.domain.model.common.Kilometer
import com.leggo.cooperativa.domain.model.common.Price
import com.leggo.cooperativa.domain.model.common.PricePerKilogram
import com.leggo.cooperativa.domain.model.common.Tax
import com.leggo.cooperativa.domain.model.common.Year
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.model.sellorder.SellOrder
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId
import com.leggo.cooperativa.domain.repositories.SellOrderRepository
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.SellOrderEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.SellOrderMongo

class SellOrderMongoRepository
(
    private val sellOrderMongo: SellOrderMongo
): SellOrderRepository
{
    override fun addSellOrder(order: SellOrder)
    {
        sellOrderMongo.save(order.toEntity())
    }

    override fun findSellOrdersBy(year: Year, productId: ProductId): List<SellOrder>
    {
        return sellOrderMongo.queryByYearAndProductId(year.amount, productId.id).map { it.toDomain() }
    }

    // ADAPTERS
    //--------------------------------------------------------------------------------------------------------

    private fun SellOrder.toEntity(): SellOrderEntity =
        SellOrderEntity(
            sellOrderId = this.sellOrderId.id.toString(),
            year = this.yearOfHarvest.amount,
            productId = this.productId.id,
            quantity = this.quantity.amount,
            marketRateDay = this.marketRateDay,
            distance = this.distance.amount,
            pricePerKilogram = this.pricePerKilogram.amount,
            logisticsPrice = this.logisticsPrice.amount,
            taxes = this.taxes.percentage)

    private fun SellOrderEntity.toDomain(): SellOrder =
        SellOrder(
            SellOrderId.of(this.sellOrderId),
            Year.of(this.year),
            ProductId(this.productId),
            Kilogram.of(this.quantity),
            this.marketRateDay,
            Kilometer.of(this.distance),
            PricePerKilogram.of(this.pricePerKilogram),
            Price.of(this.logisticsPrice),
            Tax.of(this.taxes))
}
