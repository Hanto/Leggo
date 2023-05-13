package com.leggo.cooperativa.domain.model.product;

import com.leggo.cooperativa.application.product.CreateProductCommand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest
{
    @Test
    public void whenLastPriceFor()
    {
        Product product = Product.createProduct(new ProductId("1"), "Manzana", 200f, new Price(price("0.40")));
        product.addPriceFor(price("1.40"), LocalDate.now().minusDays(1));

        Price todaysPrice = product.lastPriceFor(LocalDate.now());
        assertThat(todaysPrice).isEqualTo(new Price(price("1.40") ));

        Price yesterdaysPrice = product.lastPriceFor(LocalDate.now().minusDays(1));
        assertThat(yesterdaysPrice).isEqualTo(new Price(price("1.40") ));

        Price initialPrice = product.lastPriceFor(LocalDate.now().minusYears(100));
        assertThat(initialPrice).isEqualTo(new Price(price("0.40") ));
    }

    private BigDecimal price(String price)
    {
        return new BigDecimal(price);
    }
}
