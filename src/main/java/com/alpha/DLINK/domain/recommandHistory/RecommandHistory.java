package com.alpha.DLINK.domain.recommandHistory;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommandHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    @Column(name = "isLike")
    private Boolean isLike;

    @Column(name = "similarity")
    private Integer similarity;

    public static RecommandHistory create() {
        RecommandHistory recommandHistory = new RecommandHistory();
        recommandHistory.setSimilarity(0);
        recommandHistory.setIsLike(false);

        return recommandHistory;
    }
}