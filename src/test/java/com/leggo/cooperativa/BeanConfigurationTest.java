package com.leggo.cooperativa;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.BuyOrderValidator;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.application.sellorder.SellOrderUseCase;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.domain.services.InventoryService;
import com.leggo.cooperativa.domain.services.LogisticsService;
import com.leggo.cooperativa.domain.services.PriceService;
import com.leggo.cooperativa.domain.services.TaxService;
import com.leggo.cooperativa.domain.services.logistics.AllProductsLogistics;
import com.leggo.cooperativa.domain.services.logistics.NonPerishableLogistics;
import com.leggo.cooperativa.domain.services.logistics.PerishableLogistics;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.BuyOrderMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProducerMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.SellOrderMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.FederatedOrderMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.NonFederatedOrderMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerLimitMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProductMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.SellOrderMongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.leggo.cooperativa.domain.model.product.ProductType.NOT_PERISHABLE;
import static com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE;

@Configuration
class BeanConfigurationTest
{
    // MAIN
    //--------------------------------------------------------------------------------------------------------

    @Bean
    public ProductUseCase productUseCaseTest(
        ProductMongoRepository productMongoRepositoryTest)
    {
        return new ProductUseCase(productMongoRepositoryTest);
    }

    @Bean
    public ProducerUseCase producerUseCaseTest(
        ProductMongoRepository productMongoRepositoryTest,
        ProducerMongoRepository producerMongoRepositoryTest)
    {
        return new ProducerUseCase(producerMongoRepositoryTest, productMongoRepositoryTest);
    }

    @Bean
    public BuyOrderUSeCase buyOrderUSeCaseTest(
        ProductMongoRepository productMongoRepositoryTest,
        ProducerMongoRepository producerMongoRepositoryTest,
        BuyOrderValidator buyOrderValidatorTest,
        InventoryService inventoryServiceTest)
    {
        return new BuyOrderUSeCase(productMongoRepositoryTest, producerMongoRepositoryTest, inventoryServiceTest, buyOrderValidatorTest);
    }

    @Bean
    public SellOrderUseCase sellOrderUseCaseTest(
        PriceService priceServiceTest,
        AllProductsLogistics allProductsLogisticsTest,
        InventoryService inventoryServiceTest,
        TaxService taxServiceTest)
    {
        return new SellOrderUseCase(priceServiceTest, allProductsLogisticsTest, inventoryServiceTest, taxServiceTest);
    }

    @Bean
    public BuyOrderValidator buyOrderValidatorTest(
        ProductMongoRepository productMongoRepositoryTest,
        BuyOrderMongoRepository buyOrderMongoRepositoryTest,
        ProducerMongoRepository producerMongoRepositoryTest)
    {
        return new BuyOrderValidator(productMongoRepositoryTest, buyOrderMongoRepositoryTest, producerMongoRepositoryTest);
    }

    @Bean
    public InventoryService inventoryServiceTest(
        BuyOrderMongoRepository buyOrderMongoRepositoryTest,
        SellOrderMongoRepository sellOrderMongoRepositoryTest)
    {
        return new InventoryService(buyOrderMongoRepositoryTest, sellOrderMongoRepositoryTest);
    }

    @Bean
    public AllProductsLogistics allProductsLogisticsTest(
        ProductMongoRepository productMongoRepositoryTest)
    {
        Map<ProductType, LogisticsService>maps = new HashMap<>();
        maps.put(PERISHABLE, new PerishableLogistics());
        maps.put(NOT_PERISHABLE, new NonPerishableLogistics());

        return new AllProductsLogistics(maps, productMongoRepositoryTest);
    }

    @Bean
    public PriceService priceServiceTest(
        ProductMongoRepository productMongoRepositoryTest)
    {
        return new PriceService(productMongoRepositoryTest);
    }

    @Bean
    public TaxService taxServiceTest()
    {
        return new TaxService();
    }

    // MONGODB
    //--------------------------------------------------------------------------------------------------------

    @Bean
    public ProductMongoRepository productMongoRepositoryTest(ProductMongo productMongo)
    {
        return new ProductMongoRepository(productMongo);
    }

    @Bean
    public ProducerMongoRepository producerMongoRepositoryTest(ProducerMongo producerMongo, ProducerLimitMongo producerLimitMongo)
    {
        return new ProducerMongoRepository(producerMongo, producerLimitMongo);
    }

    @Bean
    public BuyOrderMongoRepository buyOrderMongoRepositoryTest(FederatedOrderMongo federatedOrderMongo, NonFederatedOrderMongo nonFederatedOrderMongo)
    {
        return new BuyOrderMongoRepository(federatedOrderMongo, nonFederatedOrderMongo);
    }

    @Bean
    public SellOrderMongoRepository sellOrderMongoRepositoryTestTest(SellOrderMongo sellOrderMongo)
    {
        return new SellOrderMongoRepository(sellOrderMongo);
    }
}
