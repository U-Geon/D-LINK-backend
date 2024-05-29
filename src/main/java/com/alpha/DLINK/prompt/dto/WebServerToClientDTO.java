package com.alpha.DLINK.prompt.dto;

import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WebServerToClientDTO {
    private Double similarity;
    private Long beverageId;
    private String name;
    private Nutrition nutrition;
    private Type type;
    private Integer price;
    private String photo;
    private String otherBeverage;
    private String cafe;
}