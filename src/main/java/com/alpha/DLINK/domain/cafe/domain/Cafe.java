package com.alpha.DLINK.domain.cafe.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cafe")
public class Cafe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String latitude;

    @Column(columnDefinition = "TEXT")
    private String longitude;
}
