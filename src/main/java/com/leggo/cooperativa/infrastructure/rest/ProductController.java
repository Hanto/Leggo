package com.leggo.cooperativa.infrastructure.rest;

import com.leggo.cooperativa.application.product.AddPriceCommand;
import com.leggo.cooperativa.application.product.CreateProductCommand;
import com.leggo.cooperativa.application.product.ProductUseCase;
import com.leggo.cooperativa.infrastructure.rest.requests.AddPriceRequest;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProductController
{
    private final ProductUseCase productUseCase;

    @PostMapping("/product/")
    public void createProduct(@RequestBody CreateProductRequest request)
    {
        CreateProductCommand command = new CreateProductCommand(request);

        productUseCase.createProduct(command);
    }

    @PostMapping("/product/pricePerKilogram")
    public void addPrice(@RequestBody AddPriceRequest request)
    {
        AddPriceCommand command = new AddPriceCommand(request);

        productUseCase.addPriceForProduct(command);
    }
}
