package com.leggo.cooperativa.application.producer;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import com.leggo.cooperativa.infrastructure.rest.CreateFieldsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @AllArgsConstructor
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

    public CreateFieldsCommand(CreateFieldsRequest request)
    {
        this.producerId = new ProducerId(request.getProducerId());
        this.year = new Year(request.getYear());
        this.fields = request.getFields().stream()
            .map(dto -> new FieldDTO(new ProductId(dto.getProductId()), new Hectare(dto.getHectares())))
            .collect(Collectors.toList());
    }
}
