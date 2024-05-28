package com.alpha.DLINK.domain.recommendHistory.dto;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.domain.Type;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RecommendHistoryResponseDTO {

    private Long historyId;
    private Boolean isLike;
    private Boolean isRecommended;
    private String similarity;
    private LocalDateTime createdAt;
    private BeverageDTO beverage;

    public RecommendHistoryResponseDTO(RecommendHistory history) {
        this.historyId = history.getId();
        this.isLike = history.getIsLike();
        this.isRecommended = history.getIsRecommended();
        this.createdAt = history.getCreatedAt();
        this.similarity = history.getSimilarity();
        this.beverage = new BeverageDTO(history.getBeverage());
    }

    @Getter @Setter
    static class BeverageDTO {
        private Long id;
        private String name;
        private Nutrition nutrition;
        private String cafe;
        private Type type;
        private Integer price;
        private String photo;

        public BeverageDTO(Beverage beverage) {
            this.id = beverage.getId();
            this.name = beverage.getName();
            this.nutrition = beverage.getNutrition();
            this.cafe = beverage.getCafe().getName();
            this.type = beverage.getType();
            this.price = beverage.getPrice();
            this.photo = beverage.getPhoto();
        }
    }
}
