package com.leggo.cooperativa.infrastructure.repositories.mongodb

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId
import com.leggo.cooperativa.domain.model.buyorder.Contribution
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder
import com.leggo.cooperativa.domain.model.common.Hectare
import com.leggo.cooperativa.domain.model.common.Kilogram
import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare
import com.leggo.cooperativa.domain.model.common.Kilometer
import com.leggo.cooperativa.domain.model.common.Price
import com.leggo.cooperativa.domain.model.common.PricePerKilogram
import com.leggo.cooperativa.domain.model.common.Tax
import com.leggo.cooperativa.domain.model.common.Year
import com.leggo.cooperativa.domain.model.producer.Field
import com.leggo.cooperativa.domain.model.producer.Producer
import com.leggo.cooperativa.domain.model.producer.ProducerId
import com.leggo.cooperativa.domain.model.product.MarketRate
import com.leggo.cooperativa.domain.model.product.Product
import com.leggo.cooperativa.domain.model.product.ProductId
import com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE
import com.leggo.cooperativa.domain.model.sellorder.SellOrder
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.FederatedOrderMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.NonFederatedOrderMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerLimitMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProductMongo
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.SellOrderMongo
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class MongoDbTests
{
    companion object
    {
        @JvmStatic @Container
        var mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5.0")
            .withExposedPorts(27017)

        @JvmStatic @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry): Unit =
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }

        @JvmStatic @BeforeAll
        fun beforeAll() : Unit = mongoDBContainer.start()
    }

    @Autowired lateinit var productMongo: ProductMongo
    @Autowired lateinit var producerMongo: ProducerMongo
    @Autowired lateinit var federatedOrderMongo: FederatedOrderMongo
    @Autowired lateinit var nonFederatedOrderMongo: NonFederatedOrderMongo
    @Autowired lateinit var sellOrderMongo: SellOrderMongo
    @Autowired lateinit var producerLimitMongo: ProducerLimitMongo

    @Test
    fun pim()
    {
        val product = Product(ProductId.of("NARANJA"), "naranja", KilogramsPerHectare.of(200.0),
            MarketRate(PricePerKilogram.of("2.0")), PERISHABLE)

        val fields = listOf(
            Field(ProductId("NARANJA"), Hectare.of(3.0)),
            Field(ProductId("NARANJA"), Hectare.of(3.0)))

        val producer = Producer.createProducer(ProducerId("IVAN"), "ivan")
        producer.createFieldsFor(Year.of(2023), fields)

        val federatedOrder = FederatedOrder(BuyOrderId(UUID.randomUUID()), Year.of(2023), setOf(
                Contribution(ProducerId("IVAN"), Kilogram.of(200.0)),
                Contribution(ProducerId("PEPITO"), Kilogram.of(100.0))),
            ProductId("NARANJA"), LocalDateTime.now())

        val nonFederatedOrder = NonFederatedOrder(BuyOrderId(UUID.randomUUID()), Year.of(2023),
            Contribution(ProducerId("IVAN"), Kilogram.of(100.0)),
            ProductId("NARANJA"), LocalDateTime.now())

        val nonFederatedOrder2 = NonFederatedOrder(BuyOrderId(UUID.randomUUID()), Year.of(2023),
            Contribution(ProducerId("IVAN"), Kilogram.of(200.0)),
            ProductId("LIMON"), LocalDateTime.now())

        val nonFederatedOrder3 = NonFederatedOrder(BuyOrderId(UUID.randomUUID()), Year.of(2023),
            Contribution(ProducerId("PEPITO"), Kilogram.of(200.0)),
            ProductId("NARANJA"), LocalDateTime.now())

        val sellOrder1 = SellOrder(SellOrderId(), Year.of(2023), ProductId.of("NARANJA"), Kilogram.of(20.0),
            LocalDate.now(), Kilometer.of(180.0), PricePerKilogram.of("3.0"),
            Price.of("5.0"), Tax.of(15.0))

        val sellOrder2 = SellOrder(SellOrderId(), Year.of(2023), ProductId.of("NARANJA"), Kilogram.of(20.0),
            LocalDate.now(), Kilometer.of(180.0), PricePerKilogram.of("3.0"),
            Price.of("5.0"), Tax.of(15.0))

        val sellOrder3 = SellOrder(SellOrderId(), Year.of(2023), ProductId.of("LIMON"), Kilogram.of(20.0),
            LocalDate.now(), Kilometer.of(180.0), PricePerKilogram.of("3.0"),
            Price.of("5.0"), Tax.of(15.0))

        val productRepo = ProductMongoRepository(productMongo)
        val producerRepo = ProducerMongoRepository(producerMongo,  producerLimitMongo)
        val buyOrderRepo = BuyOrderMongoRepository(federatedOrderMongo, nonFederatedOrderMongo)
        val sellOrderRepo = SellOrderMongoRepository(sellOrderMongo)

        productRepo.addProduct(product)
        producerRepo.addProducer(producer)
        producerRepo.setMaxHectaresForSmallProducer(Year.of(2023), Hectare.of(5.0))
        buyOrderRepo.addFederatedOrder(federatedOrder)
        buyOrderRepo.addNonFederatedOrder(nonFederatedOrder)
        buyOrderRepo.addNonFederatedOrder(nonFederatedOrder2)
        buyOrderRepo.addNonFederatedOrder(nonFederatedOrder3)
        sellOrderRepo.addSellOrder(sellOrder1)
        sellOrderRepo.addSellOrder(sellOrder2)
        sellOrderRepo.addSellOrder(sellOrder3)

        assertThat(productRepo.findProductById(product.productId).orElse(null))
            .usingRecursiveComparison().isEqualTo(product)

        assertThat(producerRepo.findProducerById(producer.producerId).orElse(null))
            .usingRecursiveComparison().isEqualTo(producer)

        assertThat(producerRepo.maxHectaresForSmallProducer(Year.of(2023)))
            .hasValue(Hectare.of(5.0))

        println(productRepo.findProductById(product.productId))
        println(producerRepo.findProducerById(producer.producerId))
        println(producerRepo.maxHectaresForSmallProducer(Year.of(2023)))
        println(buyOrderRepo.findFederatedOrderBy(Year.of(2023), ProductId.of("NARANJA")))
        println(buyOrderRepo.findNonFederatedOrderBy(Year.of(2023), ProductId.of("NARANJA"), ProducerId.of("IVAN")))
        println(buyOrderRepo.numberOfNonFederatedOrdersFrom(Year.of(2023), ProducerId.of("IVAN")))
        println(buyOrderRepo.findNonFederatedOrdersBy(Year.of(2023), ProductId.of("NARANJA")))
        println(sellOrderRepo.findSellOrdersBy(Year.of(2023), ProductId.of("NARANJA")))
    }
}
