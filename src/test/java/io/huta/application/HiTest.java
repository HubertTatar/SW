package io.huta.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HiTest extends TestBase {

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
                .expectStatus().is2xxSuccessful();
    }

    @AfterAll
    void afterAll() {
        server.stop();
    }


}
