package com.alpha.DLINK.domain.likeHistory.service;


import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeHistoryService {

    private final LikeHistoryRepository likeHistoryRepository;

    public List<LikeHistory> findByMember(Member member) {
        return likeHistoryRepository.findLikeHistoryByMember(member);
    }
}
