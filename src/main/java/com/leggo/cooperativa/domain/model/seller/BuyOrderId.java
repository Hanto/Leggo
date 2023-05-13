package com.leggo.cooperativa.domain.model.seller;

import lombok.Data;

import java.util.UUID;

@Data
public class BuyOrderId
{
    private final UUID id;

    public BuyOrderId()
    {
        id = UUID.randomUUID();
    }
}
