package com.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class ReactiveApiController {

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> sendIntegers() {
        return Flux.interval(Duration.ofSeconds(1)).log();
    }

    @GetMapping("/flux")
    public Flux<Integer> sendNumbers(){
        return Flux.just(1,2,3).delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping("/mono")
    public Mono<Integer> sendMonoNUmber() {
        return Mono.just(1).log();
    }

}
