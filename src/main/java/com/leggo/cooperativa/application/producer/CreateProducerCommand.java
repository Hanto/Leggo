package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;

import java.util.Set;

@Data
public class CreateProducerCommand
{
    private final ProducerId producerId;
    private final String producerName;
}
