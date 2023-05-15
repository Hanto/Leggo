package com.leggo.cooperativa;

import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductEntity;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductMongo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Testcontainers
@SpringBootTest
class MainTests
{
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0")
		.withExposedPorts(27017);

	@Autowired private ProductMongo repository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry)
	{
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@BeforeAll
	public static void beforeAll()
	{
		mongoDBContainer.start();
	}

	@Test
	public void pim()
	{
		Map<LocalDate, BigDecimal>marketRates = new HashMap<>();
		marketRates.put(LocalDate.now(), new BigDecimal("3.00"));

		ProductEntity product = ProductEntity.builder()
			.productId("NARANJAS")
			.name("naranjas")
			.kilogramsPerHectare(100d)
			.marketRates(ProductEntity.MarketRateEntity.builder().pricesByDay(marketRates).initialPricePerKilogram(new BigDecimal("2")).build()).build();

		repository.save(product);

		Optional<ProductEntity> maybeProductEntity = repository.queryByProductId("NARANJAS");

		System.out.println(maybeProductEntity);
	}

}
