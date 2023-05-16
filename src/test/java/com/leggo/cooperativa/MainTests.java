package com.leggo.cooperativa;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.buyorder.Contribution;
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Field;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.MarketRate;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.FederatedOrderMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.FederatedOrderMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProducerLimitEntityMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProducerMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProducerMongoRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Testcontainers @SpringBootTest
class MainTests
{
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0")
		.withExposedPorts(27017);

	@Autowired private ProductMongo productMongo;
	@Autowired private ProducerMongo producerMongo;
	@Autowired private ProducerLimitEntityMongo producerLimitMongo;
	@Autowired private FederatedOrderMongo federatedOrderMongo;

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
		ProductMongoRepository productRepo = new ProductMongoRepository(productMongo);
		ProducerMongoRepository producerRepo = new ProducerMongoRepository(producerMongo, producerLimitMongo);
		FederatedOrderMongoRepository federatedOrderRepo = new FederatedOrderMongoRepository(federatedOrderMongo);

		Product product = new Product(
			ProductId.of("NARANJA"), "naranja", KilogramsPerHectare.of(200),
			new MarketRate(PricePerKilogram.of("2.0")), ProductType.PERISHABLE);

		List<Field> fields = Arrays.asList(
			new Field(new ProductId("NARANJA"), Hectare.of(3)),
			new Field(new ProductId("NARANJA"), Hectare.of(3)) );

		Producer producer = Producer.createProducer(new ProducerId("IVAN"), "ivan");
		producer.createFieldsFor(Year.of(2023), fields);

		FederatedOrder federatedOrder = new FederatedOrder(new BuyOrderId(), Year.of(2023),
			new HashSet<>(Arrays.asList(
				new Contribution(new ProducerId("IVAN"), Kilogram.of(200)),
				new Contribution(new ProducerId("PEPITO"), Kilogram.of(100)))),
			new ProductId("NARANJA"), LocalDateTime.now());

		productRepo.addProduct(product);
		producerRepo.addProducer(producer);
		producerRepo.setMaxHectaresForSmallProducer(Year.of(2023), Hectare.of(5));
		federatedOrderRepo.addFederatedOrder(federatedOrder);


		System.out.println(productRepo.findProductById(product.getProductId()));
		System.out.println(producerRepo.findProducerById(producer.getProducerId()));
		System.out.println(producerRepo.maxHectaresForSmallProducer(Year.of(2023)));
		System.out.println(federatedOrderRepo.findFederatedOrderBy(Year.of(2023), ProductId.of("NARANJA")));
	}

}
