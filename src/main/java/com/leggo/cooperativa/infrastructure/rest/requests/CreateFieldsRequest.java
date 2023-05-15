package com.leggo.cooperativa.infrastructure.rest.requests;

import com.leggo.cooperativa.application.producer.CreateFieldsCommand;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class CreateFieldsRequest
{
    @NotNull private final String producerId;
    @NotNull private final Integer year;
    @NotNull private final List<FieldDTO> fields;

    @Data
    public static class FieldDTO
    {
        @NotNull private final String productId;
        @NotNull private final Float hectares;
    }

    public CreateFieldsCommand toCommand()
    {
        List<CreateFieldsCommand.FieldDTO> convertedFields = fields.stream()
            .map(dto -> new CreateFieldsCommand.FieldDTO(new ProductId(dto.getProductId()), new Hectare(dto.getHectares())))
            .toList();

        return CreateFieldsCommand.builder()
            .producerId(ProducerId.of(producerId))
            .year(Year.of(year))
            .fields(convertedFields)
            .build();
    }
}
