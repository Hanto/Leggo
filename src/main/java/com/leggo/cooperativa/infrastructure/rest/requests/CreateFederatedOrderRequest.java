package com.leggo.cooperativa.infrastructure.rest.requests;

import lombok.Data;

import java.util.Set;

@Data
public class CreateFederatedOrderRequest
{
    private final Set<String>producerIds;
    private final String productId;
    private final int year;
}
