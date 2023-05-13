package com.leggo.cooperativa.infrastructure.rest.requests;

import lombok.Data;

@Data
public class CreateProducerRequest
{
    private final String producerId;
    private final String producerName;
}
