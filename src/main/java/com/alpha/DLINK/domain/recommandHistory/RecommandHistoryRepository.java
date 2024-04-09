package com.alpha.DLINK.domain.recommandHistory;

import com.alpha.DLINK.domain.recommandHistory.RecommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommandHistoryRepository extends JpaRepository<RecommandHistory, Long> {
}