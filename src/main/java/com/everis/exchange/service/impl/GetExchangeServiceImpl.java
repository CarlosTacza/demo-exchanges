package com.everis.exchange.service.impl;

import com.everis.exchange.common.ConstantsEnum;
import com.everis.exchange.common.Utils;
import com.everis.exchange.model.Exchange;
import com.everis.exchange.repository.ExchangeRepository;
import com.everis.exchange.service.GetExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetExchangeServiceImpl implements GetExchangeService {

  private final ExchangeRepository exchangeRepository;

  public Mono<Exchange> getExchanges(String profile, LocalDate date) {
    return exchangeRepository.findByDateBetween(date, Utils.createEndDate(date))
        .collectList().flatMap((e) -> {
          if (profile.equals(ConstantsEnum.HIGH.getCode())) {
            return Mono.just(Collections.max(e, Comparator.comparing(s -> s.getSell())));
          }
          if (profile.equals(ConstantsEnum.LOW.getCode())) {
            return Mono.just(Collections.min(e, Comparator.comparing(s -> s.getSell())));
          }
          if (profile.equals(ConstantsEnum.MEDIUM.getCode())) {
            Double buyProm = e.stream().mapToDouble(Exchange::getBuy).average().orElse(0.0);
            Double sellProm = e.stream().mapToDouble(Exchange::getSell).average().orElse(0.0);
            return Mono.just(new Exchange(new Random().nextLong(), buyProm, sellProm, LocalDateTime.now()));
          } else {
            return Mono.error(new RuntimeException());
          }
        }).onErrorReturn(new Exchange());
  }
}
