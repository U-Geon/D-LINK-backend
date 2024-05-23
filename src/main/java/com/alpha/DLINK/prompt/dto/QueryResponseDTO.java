package com.alpha.DLINK.prompt.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryResponseDTO {
    private Long id;
    private String document;

    public QueryResponseDTO(Object[] result) {
        this.id = (Long) result[0];
        this.document = (String) result[1];
    }
}
