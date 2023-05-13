package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.application.producer.CreateFieldsCommand.FieldDTO;
import com.leggo.cooperativa.domain.model.producer.Field;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProducerRepository;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@AllArgsConstructor
public class ProducerUseCase
{
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;

    public void createProducer(CreateProducerCommand command)
    {
        Optional<Producer>maybeProducer = producerRepository.findProducerById(command.getProducerId());

        if (maybeProducer.isPresent())
            throw new IllegalArgumentException(format("the producer: %s, already exists", command.getProducerId()));

        Producer producer = Producer.createProducer(command.getProducerId(), command.getProducerName());

        producerRepository.addProducer(producer);
    }

    public void createFields(CreateFieldsCommand command)
    {
        Producer producer = retrieveProducer(command.getProducerId());

        List<Field> fields = command.getFieldDTOs().stream()
            .map(this::createFieldFromDTO)
            .toList();

        producer.createFieldsFor(command.getYear(), fields);

        producerRepository.updateProducer(producer);
    }

    // HELPER
    //--------------------------------------------------------------------------------------------------------

    private Producer retrieveProducer(ProducerId producerId)
    {
        Optional<Producer> maybeProducer = producerRepository.findProducerById(producerId);

        if (maybeProducer.isEmpty())
            throw new IllegalArgumentException(format("the producer: %s, doesnt exist", producerId));

        return maybeProducer.get();
    }

    private Product retrieveProduct(ProductId productId)
    {
        Optional<Product>maybeProduct = productRepository.findProductById(productId);

        if (maybeProduct.isEmpty())
            throw new IllegalArgumentException(format("the product: %s, doesnt exist", productId));

        return maybeProduct.get();
    }

    private Field createFieldFromDTO(FieldDTO dto)
    {
        Product product = retrieveProduct(dto.getProductId());
        return Field.createField(product.getProductId(), dto.getHectares());
    }
}
