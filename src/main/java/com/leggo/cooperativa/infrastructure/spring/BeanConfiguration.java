package com.leggo.cooperativa.infrastructure.spring;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.BuyOrderValidator;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.application.sellorder.SellOrderUseCase;
import com.leggo.cooperativa.domain.model.product.NonPerishableProduct;
import com.leggo.cooperativa.domain.model.product.PerishableProduct;
import com.leggo.cooperativa.domain.model.product.Product;
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
    public Map<Class<? extends Product>, LogisticsService>logisticCalculators()
    {
        Map<Class<? extends Product>, LogisticsService>maps = new HashMap<>();
        maps.put(PerishableProduct.class, new PerishableLogistics());
        maps.put(NonPerishableProduct.class, new NonPerishableLogistics());
        return maps;
    }

    @Bean
    public AllProductsLogistics allProductsLogisticCalculator(
        Map<Class<? extends Product>, LogisticsService> logisticCalculators, InMemoryDatabase database)
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
