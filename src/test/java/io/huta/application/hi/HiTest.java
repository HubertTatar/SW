package io.huta.application.hi;

import io.huta.application.TestBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HiTest extends TestBase {

    @BeforeAll
    void beforeAll() {
        startServer();
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

//    @Test
    void shouldGet404() {
        webClient
                .get()
                .uri("/hi/100")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

//    @Test
    void shouldDelete404() {
        webClient
                .delete()
                .uri("/hi/100")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void shouldCreateHi() {
        Hi testName = webClient
                .post()
                .uri("/hi")
                .body(BodyInserters.fromObject(new CreateHiCommand("testName")))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Hi.class)
                .returnResult()
                .getResponseBody();

        assertTrue(testName.getName().equals("testName"));
    }

    @Test
    void shouldGetHi() {
        Hi hi = webClient
                .post()
                .uri("/hi")
                .body(BodyInserters.fromObject(new CreateHiCommand("testName")))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Hi.class)
                .returnResult()
                .getResponseBody();


        webClient
                .get()
                .uri("/hi/" + hi.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Hi.class);
    }

    @Test
    void shouldDelete() {
        Hi hi = webClient
                .post()
                .uri("/hi")
                .body(BodyInserters.fromObject(new CreateHiCommand("testName")))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Hi.class)
                .returnResult()
                .getResponseBody();

        webClient
                .delete()
                .uri("/hi/" + hi.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }



    @AfterAll
    void afterAll() {
        stopServer();
    }

}
