package io.huta.application;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class HiRoutes {

    private final HiHandler hiHandler;

    public HiRoutes(HiHandler hiHandler) {
        this.hiHandler = hiHandler;
    }

    public RouterFunction<ServerResponse> routingFunctions() {
        return route(GET("/hi"), request -> hiHandler.handle(request));
    }
}
