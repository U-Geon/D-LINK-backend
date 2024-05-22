package com.alpha.DLINK.prompt.controller;


import com.alpha.DLINK.prompt.dto.ModelServerToWebServerDTO;
import com.alpha.DLINK.prompt.dto.PromptRequestDTO;
import com.alpha.DLINK.prompt.dto.PromptResponseDTO;
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

    @PostMapping("/test")
    public PromptResponseDTO test(@RequestBody PromptRequestDTO promptRequestDTO) {
        return mainService.findBeverageIdAndDocument(promptRequestDTO);
    }

    @PostMapping
    @Operation(summary = "프롬프트 입력", description = "프롬프트 및 카페 리스트 [이름, 위도, 경도]")
    public Mono<List<ModelServerToWebServerDTO>> sendPromptToFastApi(@RequestBody PromptRequestDTO promptRequestDTO) {
        return mainService.sendToModelServer(promptRequestDTO);
    }
}
