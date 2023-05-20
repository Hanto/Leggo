package com.leggo.cooperativa.domain.model.sellorder;

import lombok.Value;

import java.util.UUID;

@Value
public class SellOrderId
{
    UUID id;

    public SellOrderId(UUID uuid)
    {
        id = uuid;
    }

    public static SellOrderId of(String uuid)
    {
        return new SellOrderId(UUID.fromString(uuid));
    }

}
