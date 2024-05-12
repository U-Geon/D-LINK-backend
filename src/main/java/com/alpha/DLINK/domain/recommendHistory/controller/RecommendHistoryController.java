package com.alpha.DLINK.domain.recommendHistory.controller;


import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.recommendHistory.dto.HistoryAndBeverageDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.PickRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.service.RecommendHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/history")
@RequiredArgsConstructor
public class RecommendHistoryController {

    private final RecommendHistoryService recommendHistoryService;

    @GetMapping
    @Operation(summary = "날짜 별 이걸로 할래요 히스토리 조회", description = "사용자가 고른 히스토리 모음을 볼 수 있음.")
    public List<HistoryAndBeverageDTO> history(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.findBeverageByMember(member.getId());
    }

    @PostMapping("/recommend")
    @Operation(summary = "이걸로 할래요 클릭", description = "이걸로 할래요 누를 시 추천 히스토리에 데이터 생성")
    public ResponseEntity<String> pick(@AuthenticationPrincipal Member member,
                                       @RequestBody @Valid PickRequestDTO pickRequestDTO) {
        recommendHistoryService.recommend(member.getId(), pickRequestDTO.getBeverageId());

        return ResponseEntity.ok().body("{\"msg\" : \"이걸로 할래요 성공\"}");
    }
}
