package com.leggo.cooperativa.infrastructure.rest;

import com.leggo.cooperativa.application.sellorder.AddSellOrderCommand;
import com.leggo.cooperativa.application.sellorder.SellOrderUseCase;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateAddSellOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api") @RequiredArgsConstructor
public class SellOrderController
{
    private final SellOrderUseCase sellOrderUseCase;

    @PostMapping("/sellorder/majorist")
    public void createSellOrder(@RequestBody CreateAddSellOrderRequest request)
    {
        AddSellOrderCommand command = request.toCommand();

        sellOrderUseCase.createSellOrderForMajorist(command);
    }
}
