package com.alpha.DLINK.prompt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PromptResponseDTO {
    private String prompt;
    private List<QueryResponseDTO> documents;

    public PromptResponseDTO(String prompt, List<QueryResponseDTO> documents) {
        this.prompt = prompt;
        this.documents = documents;
    }
}