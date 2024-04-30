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
    private Long id;

    @Column(name = "name")
    private String name;

    // 사용법 new Nutrition()
    @Embedded
    private Nutrition nutrition;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "beverage", cascade = CascadeType.ALL)
    private List<RecommendHistory> recommandHistories = new ArrayList<>();
}