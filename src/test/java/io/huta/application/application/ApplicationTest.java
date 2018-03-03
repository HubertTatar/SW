package io.huta.application.application;

import io.huta.application.TestBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTest extends TestBase {

    @BeforeAll
    void beforeAll() {
        startServer();
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
        stopServer();
    }

}
