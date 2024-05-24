package com.alpha.DLINK.domain.recommendHistory.repository;

import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RecommendHistoryRepository extends JpaRepository<RecommendHistory, Long> {

    // 생성일 기준 최신 이걸로 할래요 히스토리 4개
    Page<RecommendHistory> findByMemberIdAndIsRecommendedTrue(Long memberId, Pageable pageable);

    // 지난 일주일 간의 히스토리 리스트
    @Query("SELECT rh FROM RecommendHistory rh WHERE rh.member.id = :memberId AND rh.createdAt BETWEEN :oneWeekAgo AND :now ORDER BY rh.createdAt DESC")
    List<RecommendHistory> findRecommendHistoriesFromLastWeek(@Param("memberId") Long memberId, @Param("oneWeekAgo") LocalDateTime oneWeekAgo, @Param("now") LocalDateTime now);
}