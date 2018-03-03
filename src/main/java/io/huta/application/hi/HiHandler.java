package io.huta.application.hi;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class HiHandler {

    private ConcurrentHashMap<Integer, Hi> cache = new ConcurrentHashMap<>();

    Mono<ServerResponse> respondWith200(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Mono<String> id1 = Mono.justOrEmpty(request.queryParam("id"));
        return id1
                .map(Integer::parseInt)
                .map(id -> cache.get(id))
                .flatMap(hi -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(hi)))
                .defaultIfEmpty(ServerResponse.notFound().build().block());
    }

    private Mono<ServerResponse> createServerResponse(Hi hi) {
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(hi));
    }
}
