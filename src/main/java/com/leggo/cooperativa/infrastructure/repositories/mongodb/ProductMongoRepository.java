package com.leggo.cooperativa.infrastructure.repositories.mongodb;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductMongoRepository implements ProductRepository
{
    private final ProductMongo mongo;

    @Override
    public void addProduct(Product product)
    {
        Map<LocalDate, BigDecimal>pricesByDay = product.getMarketRates().getPricesByDay().entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().getAmount()));

        ProductEntity.MarketRateEntity marketRateEntity = ProductEntity.MarketRateEntity.builder()
            .initialPricePerKilogram(product.getMarketRates().getInitialPricePerKilogram().getAmount())
            .pricesByDay(pricesByDay).build();

        ProductEntity entity = ProductEntity.builder()
            .productId(product.getProductId().getId())
            .name(product.getName())
            .kilogramsPerHectare(product.getKilogramsPerHectare().getAmount())
            .marketRates(marketRateEntity).build();

        mongo.save(entity);
    }

    @Override
    public Optional<Product> findProductById(ProductId productId)
    {
        return Optional.empty();
    }

    @Override
    public void updateProduct(Product product)
    {

    }
}
