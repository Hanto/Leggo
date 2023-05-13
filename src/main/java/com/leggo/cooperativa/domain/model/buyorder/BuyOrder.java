package com.leggo.cooperativa.domain.model.buyorder;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;

import java.time.LocalDateTime;

public interface BuyOrder
{
    BuyOrderId getBuyOrderId();
    Year getYear();
    ProductId getProductId();
    LocalDateTime getSoldTime();
    Hectare getHectares();
}
