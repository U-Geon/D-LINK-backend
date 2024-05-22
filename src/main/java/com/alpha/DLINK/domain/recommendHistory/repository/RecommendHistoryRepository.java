package com.alpha.DLINK.domain.recommendHistory.repository;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendHistoryRepository extends JpaRepository<RecommendHistory, Long> {
    List<RecommendHistory> findByMemberIdAndIsRecommendedTrue(Long memberId);

    List<RecommendHistory> findByMemberIdAndIsLikeTrue(Long memberId);

    Optional<RecommendHistory> findByMemberAndBeverage(Member member, Beverage beverage);
}