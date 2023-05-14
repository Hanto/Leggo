package com.leggo.cooperativa.domain.model.buyorder;

import com.leggo.cooperativa.domain.model.common.Kilogram;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.ProducerId;
import com.leggo.cooperativa.domain.model.product.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor @Getter @ToString
public class FederatedOrder implements BuyOrder
{
    private final BuyOrderId buyOrderId;
    private final Year year;
    private final Set<Contribution> contributors;
    private final ProductId productId;
    private final LocalDateTime soldTime;

    public boolean containsTheProducer(ProducerId producerId)
    {
        return contributors.stream()
            .map(Contribution::getProducerId)
            .anyMatch(producerId::equals);
    }

    public Kilogram getContributionOf(ProducerId producerId)
    {
        return contributors.stream()
            .filter(contribution -> contribution.getProducerId().equals(producerId))
            .map(Contribution::getKilograms).findFirst().orElse(Kilogram.of(0));
    }

    public Kilogram getTotalKilograms()
    {
        return contributors.stream()
            .map(Contribution::getKilograms)
            .reduce(Kilogram.of(0), Kilogram::sum);
    }
}
