package com.leggo.cooperativa.domain.services.logistics;

import com.leggo.cooperativa.domain.model.sellorder.SellOrderLogisticPriced;
import com.leggo.cooperativa.domain.model.sellorder.SellOrderProductPriced;

public interface LogisticCalculatorService
{
    SellOrderLogisticPriced calculateLogistic(SellOrderProductPriced productPriced);
}
