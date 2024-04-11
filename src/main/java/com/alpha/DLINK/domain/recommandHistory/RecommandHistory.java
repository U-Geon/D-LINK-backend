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

    private void setMember(Member member) {
        this.member = member;
        member.getRecommandHistories().add(this);
    }

    private void setBeverage(Beverage beverage) {
        this.beverage = beverage;
        beverage.getRecommandHistories().add(this);
    }
    public static RecommandHistory create(Member member, Beverage beverage) {
        RecommandHistory recommandHistory = new RecommandHistory();
        recommandHistory.setSimilarity(0);
        recommandHistory.setIsLike(false);
        recommandHistory.setMember(member);
        recommandHistory.setBeverage(beverage);

        return recommandHistory;
    }
}