package com.alpha.DLINK.domain.cafe.repository;

import com.alpha.DLINK.domain.cafe.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long>, CafeRepositoryCustom {
}