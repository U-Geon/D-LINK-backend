package com.alpha.DLINK.domain.recommendHistory.dto;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryAndBeverageDto {

    private Boolean isLike;
    private String similarity;
    private Beverage beverage;

    public HistoryAndBeverageDto(RecommendHistory history) {
        this.isLike = history.getIsLike();
        this.similarity = history.getSimilarity();
        this.beverage = history.getBeverage();
    }
}
