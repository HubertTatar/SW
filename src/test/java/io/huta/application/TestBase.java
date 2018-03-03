package io.huta.application;

import org.springframework.test.web.reactive.server.WebTestClient;

public abstract class TestBase {

    private int port = 8080;
    protected Server server = new Server(port);
    protected WebTestClient webClient = WebTestClient.bindToServer().baseUrl(String.format("http://localhost:%s", port)).build();

    protected void startServer() {
        server.start();
    }

    protected void stopServer() {
        server.stop();
    }
}
