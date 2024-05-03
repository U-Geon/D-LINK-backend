package com.alpha.DLINK.setting;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.service.BeverageService;
import com.alpha.DLINK.domain.cafe.domain.Cafe;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInitBeverage();
    }

    // test db 데이터
    /*
    * EntityManager를 사용해야 한다
    */
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInitBeverage() {
            Nutrition nutrition = new Nutrition("140", "140", "140", "140", "140", "140");
            Cafe cafe = new Cafe("스타벅스", "123", "123");
            em.persist(cafe);

            Beverage beverage = Beverage.create("아메리카노", cafe, nutrition);
            em.persist(beverage);
        }
    }
}