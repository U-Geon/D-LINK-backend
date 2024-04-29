package com.alpha.DLINK.domain.post.controller;

import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
@Slf4j
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final LikeHistoryService likeHistoryService;

    /**
     * 게시글 전체 조회
     *
     * @return
     */
    @GetMapping
    public List<Post> findAll(@AuthenticationPrincipal Member member) {
        return postService.findAll();
    }

    // 게시글 생성
    @PostMapping("/create")
    public void createPost(@AuthenticationPrincipal Member member,
            @RequestParam("files") List<MultipartFile> files,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content) {
        try {
            Member findMember = memberService.findByEmail(member.getEmail());
            postService.create(title, content, findMember, files);

//            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}