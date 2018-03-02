package io.huta.application;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HiHandler {

    Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

}
