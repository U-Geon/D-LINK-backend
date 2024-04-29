package com.alpha.DLINK.domain.recommandHistory.service;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.recommandHistory.domain.RecommandHistory;
import com.alpha.DLINK.domain.recommandHistory.repository.RecommandHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void findAllByMember(Member member) {
        List<RecommandHistory> histories = recommandHistoryRepository.findByMember(member);

    }
}