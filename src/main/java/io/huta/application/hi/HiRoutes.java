package io.huta.application.hi;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class HiRoutes {

    private final HiHandler hiHandler;

    public HiRoutes(HiHandler hiHandler) {
        this.hiHandler = hiHandler;
    }

    public RouterFunction<ServerResponse> routes() {
        return route(method(GET), request -> hiHandler.handle(request));
    }
}
