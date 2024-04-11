package com.alpha.DLINK.domain.recommandHistory;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommandHistoryService {

    private final RecommandHistoryRepository recommandHistoryRepository;

    @Transactional
    public RecommandHistory create(
            Member member,
            Beverage beverage) {
        RecommandHistory recommandHistory = RecommandHistory.create(member, beverage);
        recommandHistoryRepository.save(recommandHistory);
        return recommandHistory;
    }
}