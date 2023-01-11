package com.reactive.ReactiveAPI;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class ReactiveApiControllerTest {
    @Autowired
    WebTestClient client;

    @Test
    public void sampleTest()
    {
        Flux<Integer> fluxValues = client.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(fluxValues)
                .expectSubscription()
                .expectNext(1,2,3)
                .expectComplete()
                .verify();
    }

    @Test
    public void sampleTestForFluxStream()
    {
        Flux<Long> fluxValues = client.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(fluxValues)
                .expectNext(0L,1L,2L,3L)
                .thenCancel()
                .verify();
    }

    @Test
    public void sampleTestForMono()
    {
        Integer expectedValue = Integer.valueOf(1);

        client.get().uri("/mono")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Integer.class)
                .consumeWith(
                        (response) -> assertEquals(expectedValue, response.getResponseBody()
                        )
                );

    }
}
