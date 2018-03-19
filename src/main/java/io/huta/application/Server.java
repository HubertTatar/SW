package io.huta.application;

import io.huta.application.application.ApplicationHandler;
import io.huta.application.application.ApplicationRoutes;
import io.huta.application.hi.HiHandler;
import io.huta.application.hi.HiInMemoryRepository;
import io.huta.application.hi.HiRoutes;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.tcp.BlockingNettyContext;

import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {

    private final HttpHandler httpHandler;
    private final HttpServer httpServer;
    private BlockingNettyContext nettyContext;

    Server(int port) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        HiInMemoryRepository hiInMemoryRepository = new HiInMemoryRepository();
        HiHandler hiHandler = new HiHandler(hiInMemoryRepository, executorService);
        HiRoutes hiRoutes = new HiRoutes(hiHandler);
        ApplicationHandler applicationHandler = new ApplicationHandler();
        ApplicationRoutes applicationRoutes = new ApplicationRoutes(applicationHandler);

        Routes routes = new Routes(hiRoutes, applicationRoutes);

        this.httpHandler = toHttpHandler(routes.routes());
        this.httpServer = HttpServer.create(port);
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
