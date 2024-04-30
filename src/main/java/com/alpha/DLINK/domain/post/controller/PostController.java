package com.alpha.DLINK.domain.post.controller;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.dto.FindPostDto;
import com.alpha.DLINK.domain.post.service.PostService;
import com.alpha.DLINK.setting.S3.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
@Slf4j
public class PostController {
    private final PostService postService;
    private final S3FileService s3FileService;

    // 게시글 전체 조회
    @GetMapping
    public List<FindPostDto> findAll() {
        return postService.findAll();
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public FindPostDto findById(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return new FindPostDto(post);
    }

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@AuthenticationPrincipal Member member,
                                             @RequestParam("files") List<MultipartFile> files,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content) {
        try {
//            Member findMember = memberService.findByEmail(member.getEmail());
            postService.create(title, content, member, files);

            return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{postId}")
    public void deletePost(
            @AuthenticationPrincipal Member member,
            @PathVariable Long postId) {
        Post post = postService.findById(postId);
        List<File> files = post.getFiles();
        for (File file : files) {
            s3FileService.deletePostImageFile(file.getUrl());
        }
        postService.delete(post);

    }
}