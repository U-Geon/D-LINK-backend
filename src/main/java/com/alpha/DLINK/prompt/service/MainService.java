package com.alpha.DLINK.prompt.service;


import com.alpha.DLINK.domain.cafe.repository.CafeRepository;
import com.alpha.DLINK.prompt.dto.PromptRequestDTO;
import com.alpha.DLINK.prompt.dto.PromptResponseDTO;
import com.alpha.DLINK.prompt.dto.QueryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    @Value("${model.baseUrl}")
    private String baseUrl;

    private final WebClient.Builder webClientBuilder;
    private final CafeRepository cafeRepository;

    @Transactional(readOnly = true)
    public Mono<PromptResponseDTO> sendToModelServer(PromptRequestDTO request) {

        PromptResponseDTO promptResponseDTO = findBeverageIdAndDocument(request);

        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();

        Mono<PromptResponseDTO> promptResponseMono = webClient.post()
                .uri("/prediction")
                .bodyValue(promptResponseDTO)
                .retrieve()
                .bodyToMono(PromptResponseDTO.class);

        return promptResponseMono;
    }

    // 클라이언트에서 받은 request를 통해 query 호출 후 음료 id와 매핑되는 document 리스트 호출.
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