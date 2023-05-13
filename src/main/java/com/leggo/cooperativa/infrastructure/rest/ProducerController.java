package com.leggo.cooperativa.infrastructure.rest;

import com.leggo.cooperativa.application.producer.CreateFieldsCommand;
import com.leggo.cooperativa.application.producer.CreateProducerCommand;
import com.leggo.cooperativa.application.producer.ProducerUseCase;
import lombok.RequiredArgsConstructor;
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
        CreateProducerCommand command = new CreateProducerCommand(request);

        producerUseCase.createProducer(command);
    }

    @PostMapping("/producer/fields")
    public void createFields(@RequestBody CreateFieldsRequest request)
    {
        CreateFieldsCommand command = new CreateFieldsCommand(request);

        producerUseCase.createFields(command);
    }
}
