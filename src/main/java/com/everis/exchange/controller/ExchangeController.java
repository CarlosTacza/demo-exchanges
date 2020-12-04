package com.everis.exchange.controller;

import com.everis.exchange.events.ExchangeCreated;
import com.everis.exchange.handler.ExchangeHandler;
import com.everis.exchange.model.Exchange;
import com.everis.exchange.processors.ExchangeCreatedEventProcessor;
import com.everis.exchange.service.CreateExchangeService;
import com.everis.exchange.service.GetExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
@Api(value = "Exchange Microservice")
public class ExchangeController {

  private final CreateExchangeService createExchangeService;
  private final GetExchangeService getExchangeService;
  private final ExchangeHandler exchangeHandler;
  private final Flux<ExchangeCreated> events;

  public ExchangeController(CreateExchangeService createExchangeService,
                            GetExchangeService getExchangeService,
                            ExchangeHandler exchangeHandler,
                            ExchangeCreatedEventProcessor processor) {

    this.createExchangeService = createExchangeService;
    this.getExchangeService = getExchangeService;
    this.exchangeHandler = exchangeHandler;
    this.events = Flux.create(processor).share();
  }

  @PostMapping("/exchange")
  @ApiOperation(
      value = "Save Exchanges",
      notes = "Endpoint for register exchange",
      response = Exchange.class,
      code = 200
  )
  public Mono<Exchange> saveExchange(@Valid @RequestBody Exchange request) {
    return createExchangeService.createExchange(request);
  }

  @GetMapping(value = "/exchange/realtime", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ApiOperation(
      value = "Get Exchanges Realtime",
      notes = "Endpoint for get exchanges in real time",
      response = Exchange.class,
      code = 200
  )
  public Flux<ExchangeCreated> getExchanges() {
    log.info("Start listening collection.");
    return events;
  }

  @GetMapping("/exchange/{profile}")
  @ApiOperation(
      value = "Get Exchanges by profile",
      notes = "Endpoint for get exchanges based on profiles",
      response = Exchange.class,
      code = 200
  )
  public Mono<Exchange> getProfileExchanges(
      @PathVariable("profile") String profile,
      @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date
  ) {
    return getExchangeService.getExchanges(profile, date);
  }

  // TODO: REFACTOR HANDLER
  @GetMapping("/cache/exchange/{profile}")
  @ApiOperation(
      value = "Get Exchanges by profile",
      notes = "Endpoint for get exchanges based on profiles and save data in redis",
      response = Exchange.class,
      code = 200
  )
  public Mono<Exchange> getProfileExchangesWithRedis(
      @PathVariable("profile") String profile,
      @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date
  ) {
    return exchangeHandler.findExchanges(profile, date);
  }
}
