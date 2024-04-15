package com.alpha.DLINK.domain.likeHistory.controller;


import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeHistoryController {

    private final LikeHistoryService likeHistoryService;


    // 사용자 별 음료 히스토리 보여주기
    public List<LikeHistory> findByMember(Member member) {
        return likeHistoryService.findByMember(member);
    }

}