package com.alpha.DLINK.domain.recommendHistory.controller;


import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.recommendHistory.dto.HistoryAndBeverageDto;
import com.alpha.DLINK.domain.recommendHistory.service.RecommendHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/history")
@RequiredArgsConstructor
public class RecommendHistoryController {

    private final RecommendHistoryService recommendHistoryService;

    @GetMapping("/")
    public List<HistoryAndBeverageDto> history(@AuthenticationPrincipal Member member) {
        return recommendHistoryService.findBeverageByMember(member.getId());
    }
}
