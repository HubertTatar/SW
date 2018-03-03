package io.huta.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTest extends TestBase {

    @BeforeAll
    void beforeAll() {
        server.start();
    }

    @Test
    void shouldGet200() {
        webClient
                .get()
                .uri("/application")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @AfterAll
    void afterAll() {
        server.stop();
    }

}
