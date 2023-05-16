package com.leggo.cooperativa;

import com.leggo.cooperativa.domain.model.buyorder.BuyOrderId;
import com.leggo.cooperativa.domain.model.buyorder.Contribution;
import com.leggo.cooperativa.domain.model.buyorder.FederatedOrder;
import com.leggo.cooperativa.domain.model.buyorder.NonFederatedOrder;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.Price;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.common.Tax;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Field;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.MarketRate;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.model.product.ProductType;
import com.leggo.cooperativa.domain.model.sellorder.SellOrder;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderId;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.BuyOrderMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProducerMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.ProductMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.SellOrderMongoRepository;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.FederatedOrderMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.NonFederatedOrderMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerLimitEntityMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProducerMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.ProductMongo;
import com.leggo.cooperativa.infrastructure.repositories.mongodb.entities.SellOrderMongo;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers @SpringBootTest
class MainTests
{
	@Autowired private ProductMongo productMongo;
	@Autowired private ProducerMongo producerMongo;
	@Autowired private ProducerLimitEntityMongo producerLimitMongo;
	@Autowired private FederatedOrderMongo federatedOrderMongo;
	@Autowired private NonFederatedOrderMongo nonFederatedOrderMongo;
	@Autowired private SellOrderMongo sellOrderMongo;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0")
		.withExposedPorts(27017);

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
		BuyOrderMongoRepository buyOrderRepo = new BuyOrderMongoRepository(federatedOrderMongo, nonFederatedOrderMongo);
		SellOrderMongoRepository sellOrderRepo = new SellOrderMongoRepository(sellOrderMongo);

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

		NonFederatedOrder nonFederatedOrder = new NonFederatedOrder(new BuyOrderId(), Year.of(2023),
			new Contribution(new ProducerId("IVAN"), Kilogram.of(100)),
			new ProductId("NARANJA"), LocalDateTime.now());

		NonFederatedOrder nonFederatedOrder2 = new NonFederatedOrder(new BuyOrderId(), Year.of(2023),
			new Contribution(new ProducerId("IVAN"), Kilogram.of(200)),
			new ProductId("LIMON"), LocalDateTime.now());

		NonFederatedOrder nonFederatedOrder3 = new NonFederatedOrder(new BuyOrderId(), Year.of(2023),
			new Contribution(new ProducerId("PEPITO"), Kilogram.of(200)),
			new ProductId("NARANJA"), LocalDateTime.now());

		SellOrder sellOrder1 = new SellOrder(
			new SellOrderId(), Year.of(2023), ProductId.of("NARANJA"), Kilogram.of(20),
			LocalDate.now(), Kilometer.of(180), PricePerKilogram.of("3.0"),
			Price.of("5.0"), Tax.of(15));

		SellOrder sellOrder2 = new SellOrder(
			new SellOrderId(), Year.of(2023), ProductId.of("NARANJA"), Kilogram.of(20),
			LocalDate.now(), Kilometer.of(180), PricePerKilogram.of("3.0"),
			Price.of("5.0"), Tax.of(15));

		SellOrder sellOrder3 = new SellOrder(
			new SellOrderId(), Year.of(2023), ProductId.of("LIMON"), Kilogram.of(20),
			LocalDate.now(), Kilometer.of(180), PricePerKilogram.of("3.0"),
			Price.of("5.0"), Tax.of(15));

		productRepo.addProduct(product);
		producerRepo.addProducer(producer);
		producerRepo.setMaxHectaresForSmallProducer(Year.of(2023), Hectare.of(5));
		buyOrderRepo.addFederatedOrder(federatedOrder);
		buyOrderRepo.addNonFederatedOrder(nonFederatedOrder);
		buyOrderRepo.addNonFederatedOrder(nonFederatedOrder2);
		buyOrderRepo.addNonFederatedOrder(nonFederatedOrder3);
		sellOrderRepo.addSellOrder(sellOrder1);
		sellOrderRepo.addSellOrder(sellOrder2);
		sellOrderRepo.addSellOrder(sellOrder3);

		assertThat(productRepo.findProductById(product.getProductId()).orElse(null)).usingRecursiveComparison().isEqualTo(product);
		assertThat(producerRepo.findProducerById(producer.getProducerId()).orElse(null)).usingRecursiveComparison().isEqualTo(producer);
		assertThat(producerRepo.maxHectaresForSmallProducer(Year.of(2023))).hasValue(Hectare.of(5));

		System.out.println(productRepo.findProductById(product.getProductId()));
		System.out.println(producerRepo.findProducerById(producer.getProducerId()));
		System.out.println(producerRepo.maxHectaresForSmallProducer(Year.of(2023)));

		System.out.println(buyOrderRepo.findFederatedOrderBy(Year.of(2023), ProductId.of("NARANJA")));
		System.out.println(buyOrderRepo.findNonFederatedOrderBy(Year.of(2023), ProductId.of("NARANJA"), ProducerId.of("IVAN")));
		System.out.println(buyOrderRepo.numberOfNonFederatedOrdersFrom(Year.of(2023), ProducerId.of("IVAN")));
		System.out.println(buyOrderRepo.findNonFederatedOrdersBy(Year.of(2023), ProductId.of("NARANJA")));
		System.out.println(sellOrderRepo.findSellOrdersBy(Year.of(2023), ProductId.of("NARANJA")));
	}
}
