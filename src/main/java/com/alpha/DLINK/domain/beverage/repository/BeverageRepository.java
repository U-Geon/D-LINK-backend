package com.alpha.DLINK.domain.beverage.repository;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
    @Query(value = "SELECT b1.beverage_id, b1.name, b1.type, b1.photo, b1.price, b1.caffein, b1.fat, b1.kcal, b1.natrium, b1.protein, b1.sugar, b2.name AS other_beverage " +
            "FROM beverage b1 " +
            "JOIN ( " +
            "    SELECT beverage_id, name, cafe_id " +
            "    FROM beverage " +
            "    WHERE cafe_id = (SELECT cafe_id FROM beverage WHERE beverage_id = :beverageId) " +
            "      AND beverage_id <> :beverageId " +
            "    ORDER BY RAND() " +
            "    LIMIT 1 " +
            ") b2 ON b1.cafe_id = b2.cafe_id " +
            "WHERE b1.beverage_id = :beverageId", nativeQuery = true)
    List<Object[]> findBeverageAndRandomOtherBeverage(@Param("beverageId") Long beverageId);
}