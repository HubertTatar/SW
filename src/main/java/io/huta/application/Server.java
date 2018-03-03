package io.huta.application;

import io.huta.application.application.ApplicationHandler;
import io.huta.application.application.ApplicationRoutes;
import io.huta.application.hi.HiHandler;
import io.huta.application.hi.HiRoutes;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.tcp.BlockingNettyContext;

class Server {

    private final HttpHandler httpHandler;
    private final HttpServer httpServer;
    private BlockingNettyContext nettyContext;
    private HiHandler hiHandler;
    private HiRoutes hiRoutes;
    private ApplicationRoutes applicationRoutes;
    private ApplicationHandler applicationHandler;
    private Routes routes;

    Server(int port) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        hiHandler = new HiHandler();
        hiRoutes = new HiRoutes(hiHandler);
        applicationHandler = new ApplicationHandler();
        applicationRoutes = new ApplicationRoutes(applicationHandler);
        routes = new Routes(hiRoutes, applicationRoutes);

        applicationContext.registerBean("webHandler", WebHandler.class, () -> RouterFunctions.toWebHandler(routes.routes()));
        applicationContext.refresh();


        this.httpServer = HttpServer.create(port);
        this.httpHandler = WebHttpHandlerBuilder
                .applicationContext(applicationContext)
                .build();
    }

    void start() {
        nettyContext = httpServer.start(new ReactorHttpHandlerAdapter(httpHandler));
    }

    void stop() {
        nettyContext.shutdown();
    }

    void startAndAwait() {
        httpServer.startAndAwait(new ReactorHttpHandlerAdapter(httpHandler));
    }

    public static void main(String[] args) {
        new Server(8080).startAndAwait();
    }


}
