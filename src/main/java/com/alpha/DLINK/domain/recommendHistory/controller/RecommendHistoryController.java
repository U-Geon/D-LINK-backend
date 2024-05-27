package com.alpha.DLINK.domain.recommendHistory.controller;


import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryLikeAtHistoryPageRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryLikeAtMainRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryResponseDTO;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryRequestDTO;
import com.alpha.DLINK.domain.recommendHistory.service.RecommendHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/like-main")
    @Operation(summary = "추천 결과 창 즐겨찾기", description = "프롬프트 이후 추천 음료 결과창에서 즐겨찾기 설정 및 해제 기능")
    public ResponseEntity<String> likeAtMain(@AuthenticationPrincipal Member member,
                                             @RequestBody @Valid RecommendHistoryLikeAtMainRequestDTO requestDTO) {
        try {
            recommendHistoryService.likeAtMain(member.getId(), requestDTO.getBeverageId());
            return ResponseEntity.status(HttpStatus.OK).body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/like")
    @Operation(summary = "히스토리 페이지에서 즐겨찾기 설정 기능", description = "즐겨찾기 설정 및 해제 가능")
    public ResponseEntity<String> likeAtHistory(@AuthenticationPrincipal Member member,
                                                @RequestBody @Valid RecommendHistoryLikeAtHistoryPageRequestDTO requestDTO) {
        try {
            recommendHistoryService.likeAtHistoryPage(requestDTO.getHistoryId());
            return ResponseEntity.status(HttpStatus.OK).body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
