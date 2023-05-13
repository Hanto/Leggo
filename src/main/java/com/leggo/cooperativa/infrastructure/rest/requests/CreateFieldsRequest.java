package com.leggo.cooperativa.infrastructure.rest.requests;

import lombok.Data;

import java.util.List;

@Data
public class CreateFieldsRequest
{
    private final String producerId;
    private final int year;
    private final List<FieldDTO> fields;

    @Data
    public static class FieldDTO
    {
        private final String productId;
        private final float hectares;
    }
}
