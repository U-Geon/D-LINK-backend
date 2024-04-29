package com.alpha.DLINK.domain.recommandHistory.repository;

import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.recommandHistory.domain.RecommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommandHistoryRepository extends JpaRepository<RecommandHistory, Long> {
    List<RecommandHistory> findByMember(Member member);
}