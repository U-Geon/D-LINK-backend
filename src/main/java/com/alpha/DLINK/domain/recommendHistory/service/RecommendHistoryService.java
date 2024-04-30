package com.alpha.DLINK.domain.recommendHistory.service;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import com.alpha.DLINK.domain.recommendHistory.dto.HistoryAndBeverageDto;
import com.alpha.DLINK.domain.recommendHistory.repository.RecommendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendHistoryService {

    private final RecommendHistoryRepository recommendHistoryRepository;

    @Transactional
    public RecommendHistory create(
            Member member,
            Beverage beverage) {
        RecommendHistory recommendHistory = RecommendHistory.create(member, beverage);
        recommendHistoryRepository.save(recommendHistory);
        return recommendHistory;
    }

    public List<HistoryAndBeverageDto> findBeverageByMember(Long memberId) {
        return recommendHistoryRepository.findByMemberId(memberId).stream().map(HistoryAndBeverageDto::new).collect(Collectors.toList());
    }
}