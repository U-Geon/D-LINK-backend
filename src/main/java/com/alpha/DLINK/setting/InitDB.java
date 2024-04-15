//package com.alpha.DLINK.setting;
//
//import com.alpha.DLINK.domain.beverage.service.BeverageService;
//import com.alpha.DLINK.domain.member.service.MemberService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class InitDB {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//    // test db 데이터
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final BeverageService beverageService;
//        private final
//        public void dbInit1() {
//
//
//        }
//    }
//}