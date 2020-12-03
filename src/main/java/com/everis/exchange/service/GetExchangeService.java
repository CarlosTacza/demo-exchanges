package com.everis.exchange.service;

import com.everis.exchange.model.Exchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@FunctionalInterface
public interface GetExchangeService {
    Mono<Exchange> getExchanges(String profile, LocalDate date);
}
