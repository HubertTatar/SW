package io.huta.application.hi;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

public class HiRoutes {

    private final HiHandler hiHandler;

    public HiRoutes(HiHandler hiHandler) {
        this.hiHandler = hiHandler;
    }

    public RouterFunction<ServerResponse> routes() {
        return route(GET("/{id}"), hiHandler::getById)
                .andRoute(method(GET), hiHandler::listAll)
                .andRoute(method(POST), hiHandler::put)
                .andRoute(DELETE("/{id}"), hiHandler::delete);
    }
}
