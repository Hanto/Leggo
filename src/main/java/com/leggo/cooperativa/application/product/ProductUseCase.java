package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class ProductUseCase
{
    private final ProductRepository productRepository;

    public void createProduct(CreateProductCommand command)
    {
        Optional<Product>maybeProduct = productRepository.findProductById(command.getProductId());

        if (maybeProduct.isPresent())
            throw new IllegalArgumentException(String.format("the product: %s, already exists", command.getProductId()));

        Product product = Product.createProduct(command.getProductId(), command.getProductName(), command.getProductionPerHectare(), command.getInitialPrice());

        productRepository.addProduct(product);
    }

    public void addPriceForProduct(AddPriceCommand command)
    {
        Optional<Product>maybeProduct = productRepository.findProductById(command.getProductId());

        if (maybeProduct.isEmpty())
            throw new IllegalArgumentException(String.format("the product: %s, doesnt exist", command.getProductId()));

        Product product = maybeProduct.get();
        product.addPriceFor(command.getPrice().getAmount(), command.getDay());

        productRepository.updateProduct(product);
    }
}
