package com.leggo.cooperativa.domain.model.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.leggo.cooperativa.domain.model.product.ProductType.PERISHABLE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest
{
    @Test
    public void whenLastPriceFor()
    {
        Product product = PERISHABLE.createProduct(new ProductId("1"), "Manzana", new KilogramsPerHectare(200d), new PricePerKilogram(price("0.40")));
        product.addMarketPrice(price("1.40"), LocalDate.now().minusDays(1));

        PricePerKilogram todaysPricePerKilogram = product.lastMarketPrice(LocalDate.now());
        assertThat(todaysPricePerKilogram).isEqualTo(new PricePerKilogram(price("1.40") ));

        PricePerKilogram yesterdaysPricePerKilogram = product.lastMarketPrice(LocalDate.now().minusDays(1));
        assertThat(yesterdaysPricePerKilogram).isEqualTo(new PricePerKilogram(price("1.40") ));

        PricePerKilogram initialPricePerKilogram = product.lastMarketPrice(LocalDate.now().minusYears(100));
        assertThat(initialPricePerKilogram).isEqualTo(new PricePerKilogram(price("0.40") ));
    }

    private BigDecimal price(String price)
    {
        return new BigDecimal(price);
    }
}
