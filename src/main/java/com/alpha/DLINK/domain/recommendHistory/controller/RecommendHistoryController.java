package com.alpha.DLINK.domain.recommendHistory.controller;


import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.recommendHistory.dto.BeverageLikeRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.findRecommendHistoryResponseDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendRequestDTO;
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

    @GetMapping("/find/recommend")
    @Operation(summary = "이걸로 할래요 히스토리 조회", description = "날짜 별로 조회.")
    public List<findRecommendHistoryResponseDTO> recommendHistory(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.findByLikeThis(member.getId());
    }

    @GetMapping("/find/like")
    @Operation(summary = "추천 히스토리 즐겨찾기 기록", description = "메인 화면에 들어갈 컨트롤러")
    public List<findRecommendHistoryResponseDTO> isLikeHistory(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.findBeverageByMember(member.getId());
    }

    @PostMapping("/recommend")
    @Operation(summary = "이걸로 할래요", description = "추천 히스토리에 데이터 생성")
    public ResponseEntity<String> recommend(@AuthenticationPrincipal Member member,
                                            @RequestBody @Valid RecommendRequestDTO recommendRequestDTO) {
        recommendHistoryService.recommend(member.getId(),
                recommendRequestDTO.getBeverageId(),
                recommendRequestDTO.getSimilarity());

        return ResponseEntity.ok().body("{\"msg\" : \"이걸로 할래요 성공\"}");
    }

    @PostMapping("/like")
    @Operation(summary = "즐겨찾기 기능", description = "즐겨찾기 설정 + 해제 기능 추가")
    public void like(@AuthenticationPrincipal Member member,
            @RequestBody @Valid BeverageLikeRequestDTO beverageLikeRequestDTO) {
        recommendHistoryService.like(member.getId(), beverageLikeRequestDTO.getBeverageId());
    }
}
