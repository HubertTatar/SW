package io.huta.application.hi;

import io.huta.application.infra.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class HiInMemoryRepository implements Repository<Hi> {

    private ConcurrentHashMap<Integer, Hi> cache = new ConcurrentHashMap<>();

    public Mono<Hi> getById(Integer id) {
        return Mono.justOrEmpty(cache.get(id));
    }

    public Mono<Hi> save(Hi hi) {
        Hi toSave = hi.getId() == null ? hi.withId(getNextId()) : hi;
        cache.put(toSave.getId(), toSave);
        return Mono.just(toSave);
    }

    @Override
    public Flux<Hi> listAll() {
        return Flux.fromStream(cache.values().stream());
    }

    @Override
    public Mono<Hi> delete(Integer id) {
        return Mono.justOrEmpty(cache.remove(id));
    }

    private Integer getNextId() {
        return cache
                .keySet()
                .stream()
                .max(Comparator.naturalOrder())
                .map(i -> i + 1)
                .orElse(0);
    }

}
