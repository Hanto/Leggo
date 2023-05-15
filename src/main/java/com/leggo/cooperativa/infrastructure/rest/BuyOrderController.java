package com.leggo.cooperativa.infrastructure.rest;

import com.leggo.cooperativa.application.buyorder.BuyOrderUSeCase;
import com.leggo.cooperativa.application.buyorder.CreateNonFederatedOrderCommand;
import com.leggo.cooperativa.application.buyorder.CreatedFederatedOrderCommand;
import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateFederatedOrderRequest;
import com.leggo.cooperativa.infrastructure.rest.requests.CreateNonFederatedOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api")
@RequiredArgsConstructor
public class BuyOrderController
{
    private final BuyOrderUSeCase buyOrderUSeCase;

    @PostMapping("/buyorder/nonfedered")
    public void createNonFederedOrder(@RequestBody CreateNonFederatedOrderRequest request)
    {
        CreateNonFederatedOrderCommand command = request.toCommand();

        buyOrderUSeCase.createNonFederatedOrder(command);
    }

    @PostMapping("/buyorder/federed")
    public void createFederedOrder(@RequestBody CreateFederatedOrderRequest request)
    {
        CreatedFederatedOrderCommand command = request.toCommand();

        buyOrderUSeCase.createFederatedOrder(command);
    }

    @PostMapping("/buyOrder/limit/{year}/{hectares}")
    public void limit(@PathVariable int year, @PathVariable float hectares)
    {
        buyOrderUSeCase.setHectareLimitFor(new Year(year), new Hectare(hectares));
    }
}

