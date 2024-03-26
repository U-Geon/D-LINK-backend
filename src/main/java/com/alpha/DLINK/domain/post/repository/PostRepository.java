package com.alpha.DLINK.domain.post.repository;

import com.alpha.DLINK.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}