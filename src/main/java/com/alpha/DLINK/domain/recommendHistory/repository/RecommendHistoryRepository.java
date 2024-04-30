package com.alpha.DLINK.domain.recommendHistory.repository;

import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendHistoryRepository extends JpaRepository<RecommendHistory, Long> {
    List<RecommendHistory> findByMemberId(Long memberId);
}