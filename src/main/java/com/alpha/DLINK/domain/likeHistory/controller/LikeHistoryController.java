package com.alpha.DLINK.domain.likeHistory.controller;


import com.alpha.DLINK.domain.likeHistory.dto.LikeRequestDto;
import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class LikeHistoryController {

    private final LikeHistoryService likeHistoryService;

    @PostMapping("/like")
    @Operation(summary = "게시글 좋아요 기능", description = "body 부분에 담긴 postId를 받아 해당 게시글 좋아요 기능 구현")
    public ResponseEntity<String> clickHeart(@RequestBody @Valid LikeRequestDto likeRequestDto,
                                            @AuthenticationPrincipal Member member) {
        try {
            likeHistoryService.setLike(member.getId(), likeRequestDto.getPostId());
            return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
