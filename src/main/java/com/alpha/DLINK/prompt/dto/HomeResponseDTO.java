package com.alpha.DLINK.prompt.dto;

import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HomeResponseDTO {
    private String nickname;
    private List<RecommendHistoryResponseDTO> recommendHistory;

    public HomeResponseDTO(String nickname, List<RecommendHistory> recommendHistory) {
        this.nickname = nickname;
        this.recommendHistory = recommendHistory.stream().map(RecommendHistoryResponseDTO::new).toList();
    }
}
