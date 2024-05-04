package com.alpha.DLINK.domain.likeHistory.repository;

import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {

    Optional<LikeHistory> findByMemberAndPost(Member member, Post post);
}