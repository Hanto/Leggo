package com.leggo.cooperativa.domain.repositories;

import com.leggo.cooperativa.domain.model.product.Product;
import com.leggo.cooperativa.domain.model.product.ProductId;

import java.util.Optional;

public interface ProductRepository
{
    void addProduct(Product product);
    Optional<Product>findProductById(ProductId productId);
    void updateProduct(Product product);
}
