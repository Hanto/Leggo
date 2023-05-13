package com.leggo.cooperativa.infrastructure.rest.requests;

import lombok.Data;

@Data
public class CreateNonFederatedOrderRequest
{
    private final int year;
    private final String producerId;
    private final String productId;
}
