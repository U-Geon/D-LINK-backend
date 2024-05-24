package com.alpha.DLINK.domain.post.service;


import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.file.repository.FileRepository;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.dto.FindPostDTO;
import com.alpha.DLINK.domain.post.dto.PostDetailDTO;
import com.alpha.DLINK.domain.post.repository.PostRepository;
import com.alpha.DLINK.setting.S3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeHistoryRepository likeHistoryRepository;
    private final S3FileService s3FileService;
    private final FileRepository fileRepository;

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
                File entity = File.create(fileUrl, fileName, post);
                fileRepository.save(entity);
            }

            postRepository.save(post); // cascade에 의해 자동 영속화 됨.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게시글 전체 조회 [게시글 & 사진 조회]
    public List<FindPostDTO> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(FindPostDTO::new).collect(Collectors.toList());
    }

    // 게시글 상세 조회
    public PostDetailDTO findPost(Long memberId, Long postId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(Exception::new);
        Post post = postRepository.findById(postId).orElseThrow(Exception::new);

        Optional<LikeHistory> history = likeHistoryRepository.findByMemberAndPost(member, post);

        if(history.isPresent()) return new PostDetailDTO(post, true);
        return new PostDetailDTO(post, false);
    }


    // 게시글 아이디로 찾기
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    // 게시글 업데이트
    @Transactional
    public void update(Long postId, FindPostDTO findPostDTO) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setTitle(findPostDTO.getTitle());
        post.setContent(findPostDTO.getContent());
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