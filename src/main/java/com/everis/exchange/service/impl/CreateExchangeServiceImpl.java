package com.everis.exchange.service.impl;

import com.everis.exchange.events.ExchangeCreated;
import com.everis.exchange.model.Exchange;
import com.everis.exchange.repository.ExchangeRepository;
import com.everis.exchange.service.CreateExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateExchangeServiceImpl implements CreateExchangeService {

  private final ExchangeRepository exchangeRepository;
  private final ApplicationEventPublisher publisher;


  public Mono<Exchange> createExchange(Exchange exchange) {
    return exchangeRepository.save(exchange)
        .map((e) -> {
          publisher.publishEvent(new ExchangeCreated(e));
          return e;
        });
  }
}
