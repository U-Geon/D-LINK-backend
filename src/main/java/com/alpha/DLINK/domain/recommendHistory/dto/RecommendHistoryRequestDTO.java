package com.alpha.DLINK.domain.recommendHistory.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendHistoryRequestDTO {
    private Long beverageId;
    private String similarity;
}
