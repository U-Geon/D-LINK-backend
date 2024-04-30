package com.alpha.DLINK.domain.recommendHistory.domain;

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
public class RecommendHistory {

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
    private String similarity;

    private void setMember(Member member) {
        this.member = member;
        member.getRecommandHistories().add(this);
    }

    private void setBeverage(Beverage beverage) {
        this.beverage = beverage;
        beverage.getRecommandHistories().add(this);
    }
    public static RecommendHistory create(Member member, Beverage beverage) {
        RecommendHistory recommendHistory = new RecommendHistory();
        recommendHistory.setSimilarity("0");
        recommendHistory.setIsLike(false);
        recommendHistory.setMember(member);
        recommendHistory.setBeverage(beverage);

        return recommendHistory;
    }
}