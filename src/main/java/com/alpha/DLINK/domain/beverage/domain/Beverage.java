package com.alpha.DLINK.domain.beverage.domain;

import com.alpha.DLINK.domain.cafe.domain.Cafe;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Beverage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beverage_id")
    private Long id;

    @Column(name = "name")
    private String name;

    // 사용법 new Nutrition()
    @Embedded
    private Nutrition nutrition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "beverage", cascade = CascadeType.REMOVE)
    private List<RecommendHistory> recommendHistories = new ArrayList<>();

    public static Beverage create(String name, Cafe cafe, Nutrition nutrition, String type) {
        Beverage beverage = new Beverage();
        beverage.setName(name);
        beverage.setCafe(cafe);
        beverage.setNutrition(nutrition);
        if(type.equals("coffee")) beverage.setType(Type.COFFEE);
        else if(type.equals("ade")) beverage.setType(Type.ADE);
        else beverage.setType(Type.TEA);
        return beverage;
    }
}