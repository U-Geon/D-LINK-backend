package com.alpha.DLINK.prompt.service;


import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.domain.Type;
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

import java.util.Comparator;
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
                .sorted(Comparator.comparing(ModelServerToWebServerDTO::getSimilarity).reversed()) // similarity 기준 내림차순 정렬
                .limit(8) // 상위 6개 선택
                .map(dto -> {
                    List<Object[]> result = beverageRepository.findBeverageAndRandomOtherBeverage(dto.getId());
                    if (result.isEmpty()) {
                        return null; // 없으면 null
                    }
                    Object[] row = result.get(0);
                    Long beverageId = ((Number) row[0]).longValue();
                    String name = (String) row[1];
                    Type type = Type.valueOf((String) row[2]); // Enum 타입인 경우
                    String photo = (String) row[3];
                    Integer price = (Integer) row[4];
                    String caffein = (String) row[5];
                    String fat = (String) row[6];
                    String kcal = (String) row[7];
                    String natrium = (String) row[8];
                    String protein = (String) row[9];
                    String sugar = (String) row[10];
                    String otherBeverageName = (String) row[11];

                    Nutrition nutrition = new Nutrition(caffein, fat, kcal, natrium, protein, sugar);

                    return new WebServerToClientDTO(dto.getSimilarity(), beverageId, name, nutrition, type, price, photo, otherBeverageName, dto.getCafe());
                })
                .filter(Objects::nonNull) // null 값을 제외
                .collect(Collectors.toList());

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