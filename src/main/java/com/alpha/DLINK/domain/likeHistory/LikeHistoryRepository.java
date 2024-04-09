package com.alpha.DLINK.domain.likeHistory;

import com.alpha.DLINK.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {

    @Query("SELECT lh FROM LikeHistory lh WHERE lh.member = :member")
    List<LikeHistory> findLikeHistoryByMember(@Param("member") Member member);

}
