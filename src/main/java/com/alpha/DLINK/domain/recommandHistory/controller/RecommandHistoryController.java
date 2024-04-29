package com.alpha.DLINK.domain.recommandHistory.controller;


import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.recommandHistory.service.RecommandHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/history")
@RequiredArgsConstructor
public class RecommandHistoryController {

    private final RecommandHistoryService recommandHistoryService;
    private final MemberService memberService;

    @GetMapping("/")
    public void history(@AuthenticationPrincipal Member member) {
        recommandHistoryService.findAllByMember(member);
    }
}
