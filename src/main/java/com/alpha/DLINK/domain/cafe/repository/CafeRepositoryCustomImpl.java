package com.alpha.DLINK.domain.cafe.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CafeRepositoryCustomImpl implements CafeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findBeverageIdAndDocumentByConditions(String conditions) {
        String sql = "SELECT b.beverage_id, d.content FROM cafe c " +
                     "JOIN beverage b ON c.cafe_id = b.cafe_id " +
                     "JOIN document d ON b.document_id = d.document_id " +
                     "WHERE " + conditions;

        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
}