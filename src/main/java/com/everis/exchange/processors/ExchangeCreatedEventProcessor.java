package com.everis.exchange.processors;

import com.everis.exchange.events.ExchangeCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Slf4j
@Component
public class ExchangeCreatedEventProcessor
        implements ApplicationListener<ExchangeCreated>,
        Consumer<FluxSink<ExchangeCreated>> {

    private final Executor executor;
    private final BlockingQueue<ExchangeCreated> queue = new LinkedBlockingQueue<>();

    ExchangeCreatedEventProcessor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(ExchangeCreated event) {
        this.queue.offer(event);
    }

    @Override
    public void accept(FluxSink<ExchangeCreated> sink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    ExchangeCreated event = queue.take();
                    sink.next(event);
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }
}
