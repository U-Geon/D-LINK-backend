package com.alpha.DLINK.domain.recommandHistory.repository;

import com.alpha.DLINK.domain.recommandHistory.domain.RecommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommandHistoryRepository extends JpaRepository<RecommandHistory, Long> {
}