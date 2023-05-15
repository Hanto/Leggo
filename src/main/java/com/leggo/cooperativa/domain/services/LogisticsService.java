package com.leggo.cooperativa.domain.services;

import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;

public interface LogisticsService
{
    SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced productPriced);
}
