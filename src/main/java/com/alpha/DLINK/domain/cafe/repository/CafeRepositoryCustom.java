package com.alpha.DLINK.domain.cafe.repository;

import java.util.List;

public interface CafeRepositoryCustom {
    List<Object[]> findBeverageIdAndDocumentByConditions(String conditions);
}