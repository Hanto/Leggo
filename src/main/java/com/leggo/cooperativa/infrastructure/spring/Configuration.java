package com.leggo.cooperativa.infrastructure.spring;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.BuyOrderValidator;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.infrastructure.repositories.InMemoryDatabase;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration
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
        return new BuyOrderUSeCase(database, database, validator);
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
}
