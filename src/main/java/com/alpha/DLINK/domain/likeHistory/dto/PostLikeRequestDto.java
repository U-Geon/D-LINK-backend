package com.alpha.DLINK.domain.likeHistory.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeRequestDto {
    private Long postId;
}
