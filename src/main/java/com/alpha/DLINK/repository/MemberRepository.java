package com.alpha.DLINK.repository;

import com.alpha.DLINK.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
