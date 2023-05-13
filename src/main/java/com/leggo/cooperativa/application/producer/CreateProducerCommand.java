package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateProducerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CreateProducerCommand
{
    private final ProducerId producerId;
    private final String producerName;

    public CreateProducerCommand(CreateProducerRequest request)
    {
        this.producerId = new ProducerId(request.getProducerId());
        this.producerName = request.getProducerName();
    }
}
