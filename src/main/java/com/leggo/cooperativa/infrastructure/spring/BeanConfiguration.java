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
    public InMemoryDatabase database()
    {
        return new InMemoryDatabase();
    }

    @Bean
    public BuyOrderValidator buyOrderValidator(InMemoryDatabase database)
    {
        return new BuyOrderValidator(database, database, database);
    }

    @Bean
    public BuyOrderUSeCase buyOrderUSeCase(InMemoryDatabase database, BuyOrderValidator validator, InventoryService inventoryService)
    {
        return new BuyOrderUSeCase(database, database, inventoryService, validator);
    }

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
    public InventoryService inventoryUseCase(InMemoryDatabase database)
    {
        return new InventoryService(database, database);
    }

    @Bean
    public Map<ProductType, LogisticsService>logisticCalculators()
    {
        Map<ProductType, LogisticsService>maps = new HashMap<>();
        maps.put(PERISHABLE, new PerishableLogistics());
        maps.put(NOT_PERISHABLE, new NonPerishableLogistics());
        return maps;
    }

    @Bean
    public AllProductsLogistics allProductsLogisticCalculator(
        Map<ProductType, LogisticsService> logisticCalculators, InMemoryDatabase database)
    {
        return new AllProductsLogistics(logisticCalculators, database);
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

    @Bean
    public SellOrderUseCase sellOrderUseCase(PriceService priceService, LogisticsService logisticsService,
        InventoryService inventoryService, TaxService taxService)
    {
        return new SellOrderUseCase(priceService, logisticsService, inventoryService, taxService);
    }
}
