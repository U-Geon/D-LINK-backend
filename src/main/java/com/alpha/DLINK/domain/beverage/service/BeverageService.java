package com.alpha.DLINK.domain.beverage.service;


import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeverageService {

    private final BeverageRepository beverageRepository;

    // 음료 생성
    public void create() {

    }

    // 음료 조회
    public void findAll() {

    }

}