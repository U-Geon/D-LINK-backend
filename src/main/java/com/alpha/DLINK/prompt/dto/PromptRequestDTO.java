package com.alpha.DLINK.prompt.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
public class PromptRequestDTO {
    private String prompt;
    private List<CafeInfo> cafes;

    @Getter @Setter
    public static class CafeInfo {
        private String name;
        private String latitude;
        private String longitude;
    }
}