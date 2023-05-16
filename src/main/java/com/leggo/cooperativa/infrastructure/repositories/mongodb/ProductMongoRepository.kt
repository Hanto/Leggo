package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare
import com.leggo.cooperativa.domain.model.common.PricePerKilogram
import com.leggo.cooperativa.domain.model.product.MarketRate
import com.leggo.cooperativa.domain.model.product.Product
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.model.product.ProductType
import com.leggo.cooperativa.domain.repositories.ProductRepository
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.MarketRateEntity
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProductEntity
import java.util.Optional
import java.util.TreeMap

class ProductMongoRepository
(
    private val mongo: ProductMongo
) : ProductRepository
{
    override fun addProduct(product: Product)
    {
        mongo.save(product.toEntity())
    }

    override fun findProductById(productId: ProductId): Optional<Product>
    {
        val domain = mongo.queryByProductId(productId.id)?.toDomain()
        return Optional.ofNullable(domain)
    }

    override fun updateProduct(product: Product)
    {
        mongo.save(product.toEntity())
    }

    // ADAPTERS
    //--------------------------------------------------------------------------------------------------------

    private fun Product.toEntity(): ProductEntity
    {
        val marketRatesEntity = MarketRateEntity(
            initialPricePerKilogram = this.marketRates.initialPricePerKilogram.amount,
            pricesByDay = this.marketRates.pricesByDay.mapValues { it.value.amount }.toMap() )

        return ProductEntity(
            productId = this.productId.id,
            name = this.name,
            kilogramsPerHectare = this.kilogramsPerHectare.amount,
            marketRates = marketRatesEntity,
            productType = this.productType.name)
    }

    private fun ProductEntity.toDomain(): Product
    {
        val pricesByDay = this.marketRates.pricesByDay.mapValues { PricePerKilogram.of(it.value) }.let { TreeMap(it) }
        val initialPricePerKilogram = PricePerKilogram.of(this.marketRates.initialPricePerKilogram)
        val marketRates = MarketRate(pricesByDay, initialPricePerKilogram)

        return Product(
            ProductId.of(this.productId),
            this.name,
            KilogramsPerHectare.of(this.kilogramsPerHectare),
            marketRates,
            ProductType.valueOf(this.productType) )
    }
}
