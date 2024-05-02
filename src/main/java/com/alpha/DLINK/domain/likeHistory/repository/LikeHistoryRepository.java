package com.alpha.DLINK.domain.likeHistory.repository;

import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {

    @Query("SELECT lh FROM LikeHistory lh WHERE lh.member = :member AND lh.post = :post")
    LikeHistory findLikeHistoryByMember(@Param("member") Member member, @Param("post") Post post);
}