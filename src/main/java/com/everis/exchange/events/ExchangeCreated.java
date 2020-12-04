package com.everis.exchange.events;

import com.everis.exchange.model.Exchange;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ExchangeCreated extends ApplicationEvent {
  public ExchangeCreated(Exchange exchange) {
    super(exchange);
  }
}
