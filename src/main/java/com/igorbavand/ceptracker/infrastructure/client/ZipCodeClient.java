package com.igorbavand.ceptracker.infrastructure.client;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ZipCodeClient {

    private final WebClient webClient;

    public ZipCodeClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://wiremock:8080/").build();
    }

    public ZipCodeResponse getZipCode(String zipCode) {
        return webClient.get()
                .uri("zipcode/{zipCode}", zipCode)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("API Error")))
                .bodyToMono(ZipCodeResponse.class)
                .block();
    }

}
