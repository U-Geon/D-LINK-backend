package com.alpha.DLINK.domain.member.service;


import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 이메일 기준 회원 조회
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    // 회원 가입 시 업데이트 로직
    public void update(Member member, String name) {
        member.setNickname(name);
    }

    // 닉네임 수정
    public void updateNickname(Long memberId, String nickname) {
        Member findById = memberRepository.findById(memberId).orElseThrow();
        findById.setNickname(nickname);
    }
}