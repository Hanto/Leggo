package com.leggo.cooperativa.domain.repositories;

import com.leggo.cooperativa.domain.model.common.Hectare;
import com.leggo.cooperativa.domain.model.common.Year;
import com.leggo.cooperativa.domain.model.producer.Producer;
import com.leggo.cooperativa.domain.model.producer.ProducerId;

import java.util.Optional;

public interface ProducerRepository
{
    void addProducer(Producer producer);
    Optional<Producer> findProducerById(ProducerId producerId);
    void updateProducer(Producer producer);

    void setMaxHectaresForSmallProducer(Year year, Hectare maxHectares);
    Optional<Hectare> maxHectaresForSmallProducer(Year year);
}
