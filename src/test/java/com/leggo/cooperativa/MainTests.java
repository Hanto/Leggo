package com.leggo.cooperativa;

import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.MarketRate;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductMongoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers @SpringBootTest
class MainTests
{
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0")
		.withExposedPorts(27017);

	@Autowired private ProductMongo productMongo;

	@Bean
	public ProductMongoRepository productMongoRepository()
	{
		return new ProductMongoRepository(productMongo);
	}

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
		ProductMongoRepository repo = new ProductMongoRepository(productMongo);

		Product product = new Product(
			ProductId.of("NARANJA"), "naranja", KilogramsPerHectare.of(200),
			new MarketRate(PricePerKilogram.of("2.0")), ProductType.PERISHABLE);

		repo.addProduct(product);

		System.out.println(repo.findProductById(product.getProductId()));

	}

}
