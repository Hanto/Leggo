package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.producer.ProducerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @AllArgsConstructor @Builder
public class CreateProducerCommand
{
    private final ProducerId producerId;
    private final String producerName;
}
