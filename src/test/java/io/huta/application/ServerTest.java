package io.huta.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerTest {

    private Server server = new Server(8080);
    private WebClient webClient = WebClient.create("http://localhost:8080");


    @BeforeAll
    void beforeAll() {
        server.start();
    }

    @Test
    void shouldGet200() {
        webClient
                .get()
                .uri("/hi")
                .exchange()
                .map(ClientResponse::statusCode)
                .block()
                .is2xxSuccessful();
    }


    @AfterAll
    void afterAll() {
        server.stop();
    }


}
