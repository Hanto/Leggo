package com.leggo.cooperativa.domain.model.sellorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Kilometer;
import com.leggo.cooperativa.domain.model.common.PricePerKilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @ToString @Builder
public class SellOrderProductPriced
{
    private final Year yearOfHarvest;
    private final ProductId productId;
    private final Kilogram quantity;
    private final LocalDate marketRateDay;
    private final Kilometer distance;

    private final PricePerKilogram productPrice;
}
