package com.everis.exchange.handler;

import com.everis.exchange.common.Utils;
import com.everis.exchange.model.Exchange;
import com.everis.exchange.repository.ExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


@Component
@Slf4j
public class ExchangeHandler {

  @Autowired
  private RedisTemplate redisTemplate;

  private final ExchangeRepository exchangeRepository;

  @Autowired
  public ExchangeHandler(ExchangeRepository exchangeRepository) {
    this.exchangeRepository = exchangeRepository;
  }

  public Mono<Exchange> save(Exchange exchange) {
    return exchangeRepository.save(exchange);
  }


  public Mono<Exchange> findExchanges(String profile, LocalDate date) {

    String key = profile + date;
    ValueOperations<String, Exchange> operations = redisTemplate.opsForValue();

    boolean hasKey = redisTemplate.hasKey(key);
    if (hasKey) {
      Exchange exchange = operations.get(key);
      return Mono.create(exchMonoSink -> exchMonoSink.success(exchange));
    }

    Mono<Exchange> exchangeMono = exchangeRepository.findByDateBetween(date, Utils.createEndDate(date))
        .collectList().flatMap((e) -> {
          if (profile.equals("HIGH")) {
            return Mono.just(Collections.max(e, Comparator.comparing(s -> s.getSell())));
          }
          if (profile.equals("LOW")) {
            return Mono.just(Collections.min(e, Comparator.comparing(s -> s.getSell())));
          }
          if (profile.equals("MEDIUM")) {
            Double buyProm = e.stream().mapToDouble(Exchange::getBuy).average().orElse(0.0);
            Double sellProm = e.stream().mapToDouble(Exchange::getSell).average().orElse(0.0);
            return Mono.just(new Exchange(new Random().nextLong(), buyProm, sellProm, LocalDateTime.now()));
          } else {
            return Mono.error(new RuntimeException());
          }
        }).onErrorResume(Mono::error);

    if (exchangeMono == null)
      return exchangeMono;

    exchangeMono.subscribe(exchObj -> {
      operations.set(key, exchObj);
    });
    return exchangeMono;
  }

}


