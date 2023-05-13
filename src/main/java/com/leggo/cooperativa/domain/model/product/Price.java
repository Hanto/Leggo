package com.leggo.cooperativa.domain.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString @EqualsAndHashCode
public class Price
{
    @Getter
    private final BigDecimal amount;

    public Price(BigDecimal amount)
    {   this.amount = amount;}
}
