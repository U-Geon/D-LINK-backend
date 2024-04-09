package com.alpha.DLINK.domain.recommandHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommandHistoryService {

    private final RecommandHistoryRepository recommandHistoryRepository;

    @Transactional
    public RecommandHistory create() {
        RecommandHistory recommandHistory = RecommandHistory.create();
        recommandHistoryRepository.save(recommandHistory);
        return recommandHistory;
    }
}