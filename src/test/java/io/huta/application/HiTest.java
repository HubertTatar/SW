package io.huta.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void shouldGet404() {
        webClient
                .get()
                .uri("/hi/1")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }


    @AfterAll
    void afterAll() {
        server.stop();
    }


}
