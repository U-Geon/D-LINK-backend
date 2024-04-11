package com.alpha.DLINK.domain.beverage.domain;

import com.alpha.DLINK.domain.recommandHistory.RecommandHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Beverage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // 사용법 new Nutrition()
    @Embedded
    private Nutrition nutrition;

    @Column(name = "cafe")
    private String cafe;

    @OneToMany(mappedBy = "beverage", cascade = CascadeType.ALL)
    private List<RecommandHistory> recommandHistories = new ArrayList<>();
}