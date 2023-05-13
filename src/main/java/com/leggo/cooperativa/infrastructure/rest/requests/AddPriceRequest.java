package com.leggo.cooperativa.infrastructure.rest.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPriceRequest
{
    private final String productId;
    private final String price;
    @JsonFormat(pattern="yyyy-MM-dd")
    private final LocalDate day;
}
