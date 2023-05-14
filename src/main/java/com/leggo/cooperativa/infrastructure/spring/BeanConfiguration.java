package com.leggo.cooperativa.infrastructure.spring;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.BuyOrderValidator;
import com.leggo.cooperativa.application.inventory.InventoryUseCase;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.domain.model.product.NonPerishableProduct;
import com.leggo.cooperativa.domain.model.product.PerishableProduct;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.services.AllProductsLogisticCalculator;
import com.leggo.cooperativa.domain.services.LogisticCalculator;
import com.leggo.cooperativa.domain.services.NonPerishableLogistics;
import com.leggo.cooperativa.domain.services.PerishableLogistics;
import com.leggo.cooperativa.infrastructure.repositories.InMemoryDatabase;
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
    public BuyOrderUSeCase buyOrderUSeCase(InMemoryDatabase database, BuyOrderValidator validator)
    {
        return new BuyOrderUSeCase(database, database, database, validator);
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
    public InventoryUseCase inventoryUseCase(InMemoryDatabase database)
    {
        return new InventoryUseCase(database);
    }

    @Bean
    public Map<Class<? extends Product>, LogisticCalculator>logisticCalculators()
    {
        Map<Class<? extends Product>, LogisticCalculator>maps = new HashMap<>();
        maps.put(PerishableProduct.class, new PerishableLogistics());
        maps.put(NonPerishableProduct.class, new NonPerishableLogistics());
        return maps;
    }

    @Bean
    public AllProductsLogisticCalculator allProductsLogisticCalculator(Map<Class<? extends Product>, LogisticCalculator> logisticCalculators)
    {
        return new AllProductsLogisticCalculator(logisticCalculators);
    }
}
