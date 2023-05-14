package com.leggo.cooperativa.domain.model.producer;

import lombok.Data;

@Data
public class ProducerId
{
    private final String id;

    public static ProducerId of(String id)
    {
        return new ProducerId(id);
    }
}
