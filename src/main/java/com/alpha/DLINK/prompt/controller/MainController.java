package com.alpha.DLINK.prompt.controller;


import com.alpha.DLINK.prompt.dto.ModelServerToWebServerDTO;
import com.alpha.DLINK.prompt.dto.PromptRequestDTO;
import com.alpha.DLINK.prompt.dto.PromptResponseDTO;
import com.alpha.DLINK.prompt.dto.WebServerToClientDTO;
import com.alpha.DLINK.prompt.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Slf4j
public class MainController {

    private final MainService mainService;

    @PostMapping("/test1")
    @Operation(summary = "프롬프트 테스트 1", description = "프롬프트, 카페 리스트 요청 시 음료에 대한 감성 문서 전달.")
    public PromptResponseDTO test1(@RequestBody PromptRequestDTO promptRequestDTO) {
        return mainService.findBeverageIdAndDocument(promptRequestDTO);
    }

    @PostMapping("/test2")
    @Operation(summary = "프롬프트 테스트 2", description = "프롬프트와 카페 리스트 요청 시 각 음료들에 대한 유사도 전달.")
    public Mono<List<ModelServerToWebServerDTO>> test2(@RequestBody PromptRequestDTO promptRequestDTO) {
        PromptResponseDTO response = mainService.findBeverageIdAndDocument(promptRequestDTO);
        return mainService.sendToModelServerAndGetSimilarity(response);
    }

    @PostMapping
    @Operation(summary = "프롬프트 입력", description = "음료 정보와 추천 유사도 List")
    public Mono<List<WebServerToClientDTO>> test3(@RequestBody PromptRequestDTO promptRequestDTO) {
        PromptResponseDTO beverageIdAndDocument = mainService.findBeverageIdAndDocument(promptRequestDTO);
        return mainService.sendToModelServerAndSendToClient(beverageIdAndDocument);
    }
}
