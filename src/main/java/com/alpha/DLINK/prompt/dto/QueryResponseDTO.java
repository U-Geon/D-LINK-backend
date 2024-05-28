package com.alpha.DLINK.prompt.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryResponseDTO {
    private Long id;
    private String document;
    private String cafe;

    public QueryResponseDTO(Object[] result) {
        this.id = (Long) result[0];
        this.document = (String) result[1];
        this.cafe = (String) result[2];
    }
}