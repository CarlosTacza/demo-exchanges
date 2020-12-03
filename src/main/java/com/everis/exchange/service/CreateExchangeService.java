package com.everis.exchange.service;

import com.everis.exchange.model.Exchange;
import reactor.core.publisher.Mono;

public interface CreateExchangeService {
    Mono<Exchange> createExchange(Exchange exchange);
}
