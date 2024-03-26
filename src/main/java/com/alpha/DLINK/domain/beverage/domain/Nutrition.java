package com.alpha.DLINK.domain.beverage.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nutrition {
    private String natrium;
    private String kcal;
    private String fat;
    private String protein;
    private String sugar;
    private String caffein;
}