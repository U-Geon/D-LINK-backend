package com.alpha.DLINK.prompt.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryResponseDTO {
    private String id;
    private String document;

    public QueryResponseDTO(Object[] result) {
        this.id = result[0].toString();
        this.document = result[1].toString();
    }
}
