package io.huta.application.infra;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Repository <T> {

    Mono<T> getById(Integer id);
    Mono<T> save(T t);
    Flux<T> listAll();
    Mono<T> delete(Integer id);
}
