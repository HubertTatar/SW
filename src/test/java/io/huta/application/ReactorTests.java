package io.huta.application;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class ReactorTests {

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

    private Object returnNullMono() {
        return Mono.justOrEmpty(null);
    }

    private Object returnNull() {
        return null;
    }

    private Mono<String> notFound(Object s) throws RuntimeException {
        throw new RuntimeException("error");
    }

}
