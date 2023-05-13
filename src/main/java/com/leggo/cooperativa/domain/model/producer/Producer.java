package com.leggo.cooperativa.domain.model.producer;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Producer
{
    @Getter private final ProducerId producerId;
    @Getter private final String name;
    private final Map<Year, List<Field>> fieldsByYear = new HashMap<>();

    public static Producer createProducer(ProducerId id, String name)
    {
        return new Producer(id, name);
    }

    public void createFieldsFor(Year year, List<Field>fields)
    {
        if (fieldsByYear.containsKey(year))
            throw new IllegalArgumentException(String.format("The producer: %s, already has fields for this year", producerId));

        fieldsByYear.put(year, fields);
    }

    public Hectare getTotalHectaresFor(Year year)
    {
        return fieldsByYear.getOrDefault(year, List.of()).stream()
            .map(Field::getHectares)
            .reduce(Hectare.ofZero(), Hectare::sum);
    }

    public Hectare getTotalHectaresFor(Year year, ProductId productId)
    {
        return fieldsByYear.getOrDefault(year, List.of()).stream()
            .filter(field -> field.getProductId().equals(productId))
            .map(Field::getHectares)
            .reduce(Hectare.ofZero(), Hectare::sum);
    }
}
