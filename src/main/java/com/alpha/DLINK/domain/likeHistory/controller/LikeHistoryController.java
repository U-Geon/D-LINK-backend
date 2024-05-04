package com.alpha.DLINK.domain.likeHistory.controller;


import com.alpha.DLINK.domain.likeHistory.dto.LikeRequestDto;
import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.entity.Member;
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
    public ResponseEntity<String> clickLike(@RequestBody @Valid LikeRequestDto likeRequestDto,
                                            @AuthenticationPrincipal Member member) {
        try {
            boolean b = likeHistoryService.setLike(member.getId(), likeRequestDto.getPostId());
            return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
