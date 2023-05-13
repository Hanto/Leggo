package com.leggo.cooperativa.domain.model.seller;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.Product;

import java.time.LocalDateTime;

public interface BuyOrder
{
    BuyOrderId getBuyOrderId();
    Year getYear();
    Product getProduct();
    LocalDateTime getSoldTime();
    Hectare getTotalHectares();
}
