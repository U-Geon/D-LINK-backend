package com.alpha.DLINK.domain.beverage.domain;

import com.alpha.DLINK.domain.cafe.domain.Cafe;
import com.alpha.DLINK.domain.document.domain.Document;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "beverage")
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

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "photo", nullable = false)
    private String photo;

    private Integer price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;
}