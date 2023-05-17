package com.leggo.cooperativa.infrastructure.spring;

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
import com.leggo.cooperativa.infrastructure.repositories.memory.InMemoryDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.leggo.cooperativa.domain.model.product.ProductType.NOT_PERISHABLE;
import static com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE;

@Configuration
public class BeanConfiguration
{
    @Bean
    public ProductUseCase productUseCase(InMemoryDatabase database)
    {
        return new ProductUseCase(database);
    }

    @Bean
    public ProducerUseCase producerUseCase(InMemoryDatabase database)
    {
        return new ProducerUseCase(database, database);
    }

    @Bean
    public BuyOrderUSeCase buyOrderUSeCase(
        InMemoryDatabase database,
        BuyOrderValidator buyOrderValidator,
        InventoryService inventoryService)
    {
        return new BuyOrderUSeCase(database, database, inventoryService, buyOrderValidator);
    }

    @Bean
    public SellOrderUseCase sellOrderUseCase(
        PriceService priceService,
        AllProductsLogistics allProductsLogistics,
        InventoryService inventoryService,
        TaxService taxService)
    {
        return new SellOrderUseCase(priceService, allProductsLogistics, inventoryService, taxService);
    }

    @Bean
    public BuyOrderValidator buyOrderValidator(InMemoryDatabase database)
    {
        return new BuyOrderValidator(database, database, database);
    }


    @Bean
    public InventoryService inventoryService(InMemoryDatabase database)
    {
        return new InventoryService(database, database);
    }

    @Bean
    public AllProductsLogistics allProductsLogistics(InMemoryDatabase database)
    {
        Map<ProductType, LogisticsService>maps = new HashMap<>();
        maps.put(PERISHABLE, new PerishableLogistics());
        maps.put(NOT_PERISHABLE, new NonPerishableLogistics());

        return new AllProductsLogistics(maps, database);
    }

    @Bean
    public PriceService priceService(InMemoryDatabase database)
    {
        return new PriceService(database);
    }

    @Bean
    public TaxService taxService()
    {
        return new TaxService();
    }

    // INMEMORY
    //--------------------------------------------------------------------------------------------------------

    @Bean
    public InMemoryDatabase database()
    {
        return new InMemoryDatabase();
    }
}
