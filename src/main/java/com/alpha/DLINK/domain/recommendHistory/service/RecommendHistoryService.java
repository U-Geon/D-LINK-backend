package com.alpha.DLINK.domain.recommendHistory.service;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import com.alpha.DLINK.domain.recommendHistory.dto.HistoryAndBeverageDTO;
import com.alpha.DLINK.domain.recommendHistory.repository.RecommendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendHistoryService {

    private final MemberRepository memberRepository;
    private final BeverageRepository beverageRepository;
    private final RecommendHistoryRepository recommendHistoryRepository;

    @Transactional(readOnly = true)
    public List<HistoryAndBeverageDTO> findBeverageByMember(Long memberId) {
        return recommendHistoryRepository.findByMemberId(memberId).stream().map(HistoryAndBeverageDTO::new).collect(Collectors.toList());
    }

    // 이걸로 할래요!
    public void recommend(Long memberId, Long beverageId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Beverage beverage = beverageRepository.findById(beverageId).orElse(null);
        RecommendHistory recommendHistory = RecommendHistory.create(member, beverage);

        recommendHistoryRepository.save(recommendHistory);
    }
}