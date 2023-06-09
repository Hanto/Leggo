package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor @Builder
public class CreateFieldsCommand
{
    private final ProducerId producerId;
    private final Year year;
    private final List<FieldDTO> fields;

    @Data
    public static class FieldDTO
    {
        private final ProductId productId;
        private final Hectare hectares;
    }
}
