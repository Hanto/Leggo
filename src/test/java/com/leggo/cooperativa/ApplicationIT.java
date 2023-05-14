package com.leggo.cooperativa;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.BuyOrderValidator;
import com.leggo.cooperativa.application.buyorder.CreateNonFederatedOrderCommand;
import com.leggo.cooperativa.application.buyorder.CreatedFederatedOrderCommand;
import com.leggo.cooperativa.application.inventory.InventoryUseCase;
import com.leggo.cooperativa.application.producer.CreateFieldsCommand;
import com.leggo.cooperativa.application.producer.CreateFieldsCommand.FieldDTO;
import com.leggo.cooperativa.application.producer.CreateProducerCommand;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.application.product.AddPriceCommand;
import com.leggo.cooperativa.application.product.CreateProductCommand;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.KilogramsPerHectare;
import com.leggo.cooperativa.domain.model.product.PricePerKilogram;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.repositories.InMemoryDatabase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE;

class ApplicationIT
{
    private final InMemoryDatabase database = new InMemoryDatabase();
    private final BuyOrderValidator validator = new BuyOrderValidator(database, database, database);
    private final BuyOrderUSeCase buyOrderUSeCase = new BuyOrderUSeCase(database, database, database, validator);
    private final ProductUseCase productUseCase = new ProductUseCase(database);
    private final ProducerUseCase producerUseCase = new ProducerUseCase(database, database);
    private final InventoryUseCase inventoryUseCase = new InventoryUseCase(database);

    @Test
    public void pim()
    {
        CreateProductCommand createNaranja = new CreateProductCommand(
            new ProductId("NARANJA"), "naranja", new KilogramsPerHectare(200d), new PricePerKilogram(new BigDecimal("1.50")), PERISHABLE);

        CreateProductCommand createLimon = new CreateProductCommand(
            new ProductId("LIMON"), "limon", new KilogramsPerHectare(300d), new PricePerKilogram(new BigDecimal("2.50")), PERISHABLE);

        productUseCase.createProduct(createNaranja);
        productUseCase.createProduct(createLimon);

        AddPriceCommand addPriceCommand = new AddPriceCommand(new ProductId("NARANJA"), new PricePerKilogram(new BigDecimal("1.50")), LocalDate.now());
        productUseCase.addPriceForProduct(addPriceCommand);

        CreateProducerCommand createPepito = new CreateProducerCommand(new ProducerId("PEPITO"), "pepito");
        CreateProducerCommand createJuanito = new CreateProducerCommand(new ProducerId("JUANITO"), "juanito");

        producerUseCase.createProducer(createPepito);
        producerUseCase.createProducer(createJuanito);

        List<FieldDTO>pepitoFields = Arrays.asList(
            new FieldDTO(new ProductId("NARANJA"), Hectare.of(4)),
            new FieldDTO(new ProductId("LIMON"), Hectare.of(0.5f)) );
        CreateFieldsCommand pepitoFieldsCommand = new CreateFieldsCommand(new ProducerId("PEPITO"), new Year(2023), pepitoFields);

        List<FieldDTO>juanitoFields = Arrays.asList(
            new FieldDTO(new ProductId("NARANJA"), Hectare.of(2)),
            new FieldDTO(new ProductId("LIMON"), Hectare.of(1)) );
        CreateFieldsCommand juanitoFieldsCommand = new CreateFieldsCommand(new ProducerId("JUANITO"), new Year(2023), juanitoFields);

        producerUseCase.createFields(pepitoFieldsCommand);
        producerUseCase.createFields(juanitoFieldsCommand);

        buyOrderUSeCase.setHectareLimitFor(new Year(2023), Hectare.of(5));
        buyOrderUSeCase.setHectareLimitFor(new Year(2024), Hectare.of(5));

        CreatedFederatedOrderCommand pepitoJuanitoSellers = new CreatedFederatedOrderCommand(
            new HashSet<>(Arrays.asList(new ProducerId("PEPITO"), new ProducerId("JUANITO"))),
            new ProductId("NARANJA"),
            new Year(2023) );

        buyOrderUSeCase.createFederatedOrder(pepitoJuanitoSellers);

        CreateNonFederatedOrderCommand pepitoSeller = new CreateNonFederatedOrderCommand(
            new Year(2023),
            new ProducerId("PEPITO"),
            new ProductId("LIMON") );

        buyOrderUSeCase.createNonFederatedOrder(pepitoSeller);

        System.out.println(inventoryUseCase.totalKilogramsBought(Year.of(2023), ProductId.of("NARANJA")));
        System.out.println(inventoryUseCase.totalKilogramsBoughtFrom(Year.of(2023), ProductId.of("NARANJA"), ProducerId.of("PEPITO")));
        System.out.println(inventoryUseCase.totalKilogramsBoughtFrom(Year.of(2023), ProductId.of("NARANJA"), ProducerId.of("JUANITO")));
    }

}
