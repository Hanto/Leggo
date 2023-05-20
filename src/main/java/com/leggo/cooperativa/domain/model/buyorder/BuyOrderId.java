package com.leggo.cooperativa.domain.model.buyorder;

import lombok.Data;

import java.util.UUID;

@Data
public class BuyOrderId
{
    private final UUID id;

    public BuyOrderId(UUID uuid)
    {
        id = uuid;
    }
    public static BuyOrderId of(String uuid)
    {
        return new BuyOrderId(UUID.fromString(uuid));
    }
}
