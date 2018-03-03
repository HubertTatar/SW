package io.huta.application;

import io.huta.application.application.ApplicationRoutes;
import io.huta.application.hi.HiRoutes;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

class Routes {

    private final HiRoutes hiRoutes;
    private final ApplicationRoutes applicationRoutes;

    Routes(HiRoutes hiRoutes, ApplicationRoutes applicationRoutes) {
        this.hiRoutes = hiRoutes;
        this.applicationRoutes = applicationRoutes;
    }

    RouterFunction<ServerResponse> routes() {
        return nest(path("/hi"), hiRoutes.routes())
                .andNest(path("/application"), applicationRoutes.routes());
    }
}
