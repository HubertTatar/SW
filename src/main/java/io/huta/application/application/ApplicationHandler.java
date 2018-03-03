package io.huta.application.application;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ApplicationHandler {

    Mono<ServerResponse> get(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }
}
