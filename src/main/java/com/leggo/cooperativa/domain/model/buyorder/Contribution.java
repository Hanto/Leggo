package com.leggo.cooperativa.domain.model.buyorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import lombok.Data;

@Data
public class Contribution
{
    private final ProducerId producerId;
    private final Kilogram kilograms;
}
