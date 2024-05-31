package com.alpha.DLINK.prompt.controller;


import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.recommendHistory.service.RecommendHistoryService;
import com.alpha.DLINK.prompt.dto.*;
import com.alpha.DLINK.prompt.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService mainService;
    private final RecommendHistoryService recommendHistoryService;

    @GetMapping
    @Operation(summary = "홈 화면", description = "최신순 이걸로 할래요 4개 및 사용자 닉네임")
    public HomeResponseDTO home(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.home(member.getId());
    }

    @PostMapping("/prompt")
    @Operation(summary = "프롬프트 입력", description = "음료 정보와 추천 유사도 List")
    public Mono<List<WebServerToClientDTO>> test3(@RequestBody PromptRequestDTO promptRequestDTO) {
        PromptResponseDTO beverageIdAndDocument = mainService.findBeverageIdAndDocument(promptRequestDTO);
        if(beverageIdAndDocument == null) return Mono.empty();
        return mainService.sendToModelServerAndSendToClient(beverageIdAndDocument);
    }
}
