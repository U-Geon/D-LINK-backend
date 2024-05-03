package com.alpha.DLINK.domain.cafe.service;

import com.alpha.DLINK.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    public void create() {

    }
}