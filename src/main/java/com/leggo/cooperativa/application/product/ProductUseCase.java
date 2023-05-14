package com.leggo.cooperativa.application.product;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;

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

        Product product = command.getProductType().createProduct(command.getProductId(), command.getProductName(), command.getKilogramsPerHectare(), command.getInitialPricePerKilogram());

        productRepository.addProduct(product);
    }

    public void addPriceForProduct(AddPriceCommand command)
    {
        Optional<Product>maybeProduct = productRepository.findProductById(command.getProductId());

        if (maybeProduct.isEmpty())
            throw new IllegalArgumentException(String.format("the product: %s, doesnt exist", command.getProductId()));

        Product product = maybeProduct.get();
        product.addMarketPrice(command.getPricePerKilogram().getAmount(), command.getDay());

        productRepository.updateProduct(product);
    }
}
