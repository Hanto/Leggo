package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.producer.ProducerId;
import lombok.Data;

@Data
public class CreateProducerCommand
{
    private final ProducerId producerId;
    private final String producerName;
}
