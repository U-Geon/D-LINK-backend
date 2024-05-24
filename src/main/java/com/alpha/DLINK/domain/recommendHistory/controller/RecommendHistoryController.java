package com.alpha.DLINK.domain.recommendHistory.controller;


import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryLikeRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryResponseDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.service.RecommendHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class RecommendHistoryController {

    private final RecommendHistoryService recommendHistoryService;

    @GetMapping
    @Operation(summary = "히스토리 API", description = "현재 날짜 기준으로 지난 일주일 간의 history")
    public List<RecommendHistoryResponseDTO> history(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.historyByWeek(member.getId());
    }

    @PostMapping("/recommend")
    @Operation(summary = "이걸로 할래요", description = "이걸로 할래요 히스토리 생성")
    public ResponseEntity<String> recommend(@AuthenticationPrincipal Member member,
                                            @RequestBody @Valid RecommendHistoryRequestDTO recommendHistoryRequestDTO) {
        recommendHistoryService.recommend(member.getId(),
                recommendHistoryRequestDTO.getBeverageId(),
                recommendHistoryRequestDTO.getSimilarity());

        return ResponseEntity.ok().body("{\"msg\" : \"이걸로 할래요 성공\"}");
    }

    @PostMapping("/like")
    @Operation(summary = "즐겨찾기 기능", description = "즐겨찾기 설정 + 해제 기능")
    public void like(@AuthenticationPrincipal Member member,
            @RequestBody @Valid RecommendHistoryLikeRequestDTO recommendHistoryLikeRequestDTO) {
        recommendHistoryService.like(member.getId(), recommendHistoryLikeRequestDTO.getBeverageId(), recommendHistoryLikeRequestDTO.getHistoryId());
    }
}
