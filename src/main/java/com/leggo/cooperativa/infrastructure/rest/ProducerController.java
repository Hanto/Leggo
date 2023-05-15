package com.leggo.cooperativa.infrastructure.rest;

import com.leggo.cooperativa.application.producer.CreateFieldsCommand;
import com.leggo.cooperativa.application.producer.CreateProducerCommand;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateFieldsRequest;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateProducerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProducerController
{
    private final ProducerUseCase producerUseCase;

    @PostMapping("/producer")
    public void createProducer(@RequestBody CreateProducerRequest request)
    {
        CreateProducerCommand command = request.toCommand();

        producerUseCase.createProducer(command);
    }

    @PostMapping("/producer/fields")
    public void createFields(@RequestBody CreateFieldsRequest request)
    {
        CreateFieldsCommand command = request.toCommand();

        producerUseCase.createFields(command);
    }

    @PostMapping("/producer/limit/{year}/{hectares}")
    public void limit(@PathVariable int year, @PathVariable float hectares)
    {
        producerUseCase.setHectareLimitFor(new Year(year), new Hectare(hectares));
    }
}
