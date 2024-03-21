package com.alpha.DLINK.service;


import com.alpha.DLINK.entity.Post;
import com.alpha.DLINK.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
    public void create(Post post) {
        postRepository.save(post);
    }

    // 게시글 업데이트
    public void update(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 게시글 삭제
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
