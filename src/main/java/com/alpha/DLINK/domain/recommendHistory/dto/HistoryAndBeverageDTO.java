package com.alpha.DLINK.domain.recommendHistory.dto;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class HistoryAndBeverageDTO {

    private Boolean isLike;
    private String similarity;
    private LocalDateTime createdAt;
    private Beverage beverage;

    public HistoryAndBeverageDTO(RecommendHistory history) {
        this.isLike = history.getIsLike();
        this.createdAt = history.getCreatedAt();
        this.similarity = history.getSimilarity();
        this.beverage = history.getBeverage();
    }
}
