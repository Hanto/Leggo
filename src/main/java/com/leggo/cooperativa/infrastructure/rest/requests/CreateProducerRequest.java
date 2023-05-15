package com.leggo.cooperativa.infrastructure.rest.requests;

import com.leggo.cooperativa.application.producer.CreateProducerCommand;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CreateProducerRequest
{
    @NotNull private final String producerId;
    @NotNull private final String producerName;

    public CreateProducerCommand toCommand()
    {
        return CreateProducerCommand.builder()
            .producerId(ProducerId.of(producerId))
            .producerName(producerName)
            .build();
    }
}
