package com.alpha.DLINK.prompt.service;


import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import com.alpha.DLINK.domain.cafe.repository.CafeRepository;
import com.alpha.DLINK.prompt.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    @Value("${baseUrl.model}")
    private String baseUrl;

    private final WebClient.Builder webClientBuilder;
    private final CafeRepository cafeRepository;
    private final BeverageRepository beverageRepository;

    // 모델 서버에 넘겨준 정보를 토대로 받은 음료 id와 유사도와 카페 이름을 client에게 음료와 유사도 전송.
    @Transactional(readOnly = true)
    public Mono<List<WebServerToClientDTO>> sendToModelServerAndSendToClient(PromptResponseDTO promptResponseDTO) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();

        Mono<List<ModelServerToWebServerDTO>> listMono = webClient.post()
                .uri("/prediction")
                .bodyValue(promptResponseDTO)
                .retrieve()
                .bodyToFlux(ModelServerToWebServerDTO.class)
                .collectList();

        return listMono.flatMap(this::findBeveragesWithSimilarity);
    }

    private Mono<List<WebServerToClientDTO>> findBeveragesWithSimilarity(List<ModelServerToWebServerDTO> dtos) {
        List<WebServerToClientDTO> beverageSimilarityList = dtos.stream()
                .map(dto -> {
                    Beverage beverage = beverageRepository.findById(dto.getId()).orElse(null);
                    return new WebServerToClientDTO(dto.getSimilarity(), Objects.requireNonNull(beverage), dto.getCafe());
                }).collect(Collectors.toList());

        return Mono.just(beverageSimilarityList);
    }

    // 클라이언트에서 받은 request를 통해 query 호출 후 음료 id와 매핑되는 document 리스트와 카페 이름 호출.
    @Transactional(readOnly = true)
    public PromptResponseDTO findBeverageIdAndDocument(PromptRequestDTO request) {
        List<PromptRequestDTO.CafeInfo> cafes = request.getCafes();

        if (cafes.isEmpty()) throw new RuntimeException("No cafe found");

        // 동적 네이티브 쿼리 생성
        List<String> conditions = cafes.stream()
                .map(cafe -> String.format("(c.name = '%s' AND c.latitude = '%s' AND c.longitude = '%s')",
                        cafe.getName(), cafe.getLatitude(), cafe.getLongitude())).toList();

        String conditionsString = String.join(" OR ", conditions);

        List<QueryResponseDTO> list = cafeRepository.findBeverageIdAndDocumentByConditions(conditionsString).stream().map(QueryResponseDTO::new).toList();

        return new PromptResponseDTO(request.getPrompt(), list);
    }
}