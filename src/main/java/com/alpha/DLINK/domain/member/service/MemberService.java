package com.alpha.DLINK.domain.member.service;


import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    // 전체 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 이메일 기준 회원 조회
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    // 회원 정보 수정
    @Transactional
    public Member update(Member member, String name) {
        member.setNickname(name);
        return member;
    }

    // 회원 탈퇴
    @Transactional
    public void delete(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        if(byId.isPresent()) {
            Member member = byId.get();
            memberRepository.delete(member);
        }
    }
}