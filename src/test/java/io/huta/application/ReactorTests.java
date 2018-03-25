package io.huta.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
class ReactorTests {

    private static final

    @Test
    void test() {
        Mono
                .empty()
                .doOnNext(a -> System.out.println(a))
                .flatMap(o -> Mono.justOrEmpty('a'))
                .defaultIfEmpty('a')
                .doOnNext(a -> System.out.println(a))
                .subscribe();

        Mono
                .empty()
                .doOnNext(a -> System.out.println(a))
                .map(o -> {
                    System.out.println(o);
                    return Mono.empty();
                })
                .defaultIfEmpty(Mono.justOrEmpty('a'))
                .doOnNext(a -> System.out.println(a))
                .subscribe();
    }

    @Test
    void test2() {
        Mono.empty()
            .map(s -> notFound(s))
            .doOnNext(s -> Mono.just("s"))
            .doOnError(e -> System.out.println("err " + e))
            .doOnSuccess(s -> System.out.println("suc " + s))
            .subscribe(System.out::println);

        Mono.justOrEmpty(returnNull())
                .doOnNext(s -> System.out.println("nex " + s))
                .doOnError(s -> System.out.println("err " + s))
                .doOnSuccess(s -> System.out.println("suc " + s))
                .subscribe();


        Mono.justOrEmpty(returnNullMono())
                .map(s -> s)
                .doOnNext(s -> System.out.println("rnex " + s))
                .doOnError(s -> System.out.println("rerr " + s))
                .doOnSuccess(s -> System.out.println("rsuc " + s))
                .subscribe();
    }

    @Test
    void test3() {
        Flux
                .just(1,2,3,4,5,6,7,8,9)
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .doOnNext(i -> log.info(i + ": " + Thread.currentThread().getName()))
                .subscribe();

        Flux
                .just(1,2,3,4,5,6,7,8,9)
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .doOnNext(i -> log.info(i + ": " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.elastic());

        Something a = new Something();
        SomethingOuter b = new SomethingOuter();
        Flux
                .just(1,2,3,4,5,6,7,8,9)
                .filter(i -> i % 2 == 0)
                .flatMap(a::square)
                .map(b::square)
                .doOnNext(i -> log.info(i + ": " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.elastic());
    }

    private Object returnNullMono() {
        return Mono.justOrEmpty(null);
    }

    private Object returnNull() {
        return null;
    }

    private Mono<String> notFound(Object s) throws RuntimeException {
        throw new RuntimeException("error");
    }

    private class Something {
        Mono<Integer> square(Integer i) {
            log.info("square " + i);
            return Mono.just(i * 2);
        }
    }

}
