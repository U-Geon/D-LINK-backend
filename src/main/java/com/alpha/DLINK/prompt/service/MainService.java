package com.alpha.DLINK.prompt.service;


import com.alpha.DLINK.prompt.dto.PromptRequest;
import com.alpha.DLINK.prompt.dto.PromptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MainService {

    private final WebClient.Builder webClientBuilder;

    public Mono<PromptResponse> sendToModelServer(PromptRequest request) {
        WebClient webClient = webClientBuilder.baseUrl("http://fastapi-server-url").build();

        return webClient.post()
                .uri("/prediction")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PromptResponse.class);
    }
}