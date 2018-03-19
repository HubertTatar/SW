package io.huta.application.hi;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Random {

    @Test
    void lensTest() {
        HiLens lens = new HiLens();
        assertTrue(lens.getGet().apply(new Hi(1, "dwa")).equals("dwa"));
        assertTrue(lens.getSet().apply(new Hi(1, "trzy"), "trzy").getName().equals("trzy"));
    }

    abstract class Lens<T, R> {
        private Function<T, R> get;
        private BiFunction<T, R, T> set;

        Lens(Function<T, R> get, BiFunction<T, R, T> set) {
            this.get = get;
            this.set = set;
        }

        Function<T, R> getGet() {
            return get;
        }

        BiFunction<T, R, T> getSet() {
            return set;
        }
    }

    class HiLens extends Lens<Hi, String> {
        HiLens() {
            super(Hi::getName, Hi::withName);
        }
        //https://www.codementor.io/eh3rrera/using-java-8-method-reference-du10866vx
    }
}
