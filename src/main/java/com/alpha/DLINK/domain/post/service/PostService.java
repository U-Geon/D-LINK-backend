package com.alpha.DLINK.domain.post.service;


import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.dto.FindPostDTO;
import com.alpha.DLINK.domain.post.repository.PostRepository;
import com.alpha.DLINK.setting.S3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final S3FileService s3FileService;

    // 게시글 생성 로직
    @Transactional
    public void create(String title,
                       String content,
                       List<MultipartFile> files,
                       String nickname) {

        try {
            Post post = Post.create(title, content, nickname);

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String fileUrl = s3FileService.createPostImageFile("post_image", file);
                File.create(fileUrl, fileName, post);
            }

            postRepository.save(post); // cascade에 의해 자동 영속화 됨.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게시글 전체 조회
    // DTO 써서 post랑 file이랑 같이 조회!
    public List<FindPostDTO> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(FindPostDTO::new).collect(Collectors.toList());
    }

    // 게시글 아이디로 찾기
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // 게시글 업데이트
    @Transactional
    public void update(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Post post) {
        List<File> files = post.getFiles();
        for (File file : files) {
            s3FileService.deletePostImageFile("post_image", file.getUrl());
        }
        postRepository.delete(post);
    }
}