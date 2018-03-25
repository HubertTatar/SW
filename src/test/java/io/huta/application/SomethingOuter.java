package io.huta.application;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class SomethingOuter  {
    Mono<Integer> square(Integer i) {
        log.info("square " + i);
        return Mono
                .just(i * 2)
                .doOnNext(a -> log.info(a + ": " + Thread.currentThread().getName()));
    }
}
