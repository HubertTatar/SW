package io.huta.application.application;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

public class ApplicationRoutes {

    private final ApplicationHandler applicationHandler;

    public ApplicationRoutes(ApplicationHandler applicationHandler) {
        this.applicationHandler = applicationHandler;
    }

    public RouterFunction<ServerResponse> routes() {
        return route(method(GET), applicationHandler::get);
    }

}
