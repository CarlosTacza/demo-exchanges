package com.everis.exchange.repository;

import com.everis.exchange.model.Exchange;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface ExchangeRepository extends ReactiveCrudRepository<Exchange, String> {
  Flux<Exchange> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

