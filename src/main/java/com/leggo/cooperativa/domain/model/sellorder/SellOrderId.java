package com.leggo.cooperativa.domain.model.sellorder;

import lombok.Value;

import java.util.UUID;

@Value
public class SellOrderId
{
    UUID id;

    public SellOrderId()
    {
        id = UUID.randomUUID();
    }
    public SellOrderId(String uuid)
    {
        id = UUID.fromString(uuid);
    }

}
