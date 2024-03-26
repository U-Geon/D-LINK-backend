package com.alpha.DLINK.domain.beverage.repository;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
}
