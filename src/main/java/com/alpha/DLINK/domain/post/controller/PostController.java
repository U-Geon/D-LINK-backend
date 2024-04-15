package com.alpha.DLINK.domain.post.controller;

import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final LikeHistoryService likeHistoryService;

    /**
     * 사용자별 게시글 조회
     */
    @GetMapping("/{memberId}")
    public void findAll(@PathVariable("memberId") Long memberId) {

    }
}