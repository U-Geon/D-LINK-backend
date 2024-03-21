package com.alpha.DLINK.service;


import com.alpha.DLINK.entity.Member;
import com.alpha.DLINK.repository.MemberRepository;
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

    // 회원 가입
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 회원 정보 수정
    public Long update(Long id, String name, String password) {
        Optional<Member> findMember = memberRepository.findById(id);
        if(findMember.isPresent()) {
            Member member = findMember.get();
            member.setName(name);
            member.setPassword(password);
        }
        return id;
    }

    // 회원 탈퇴
    public void delete(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        if(byId.isPresent()) {
            Member member = byId.get();
            memberRepository.delete(member);
        }
    }
}
