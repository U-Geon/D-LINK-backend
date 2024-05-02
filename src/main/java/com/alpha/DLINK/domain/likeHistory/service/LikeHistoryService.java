package com.alpha.DLINK.domain.likeHistory.service;


import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeHistoryService {

    private final LikeHistoryRepository likeHistoryRepository;

    // 좋아요 체크
    public Boolean findByMemberAndPost(Member member, Post post) {
        return likeHistoryRepository.findLikeHistoryByMember(member, post).getIsLike();
    }

    // 좋아요 추가
    public LikeHistory addLike(LikeHistory likeHistory) {
        likeHistory.setIsLike(true);
        return likeHistory;
    }

    // 좋아요 취소
    public LikeHistory cancelLike(LikeHistory likeHistory) {
        likeHistory.setIsLike(false);
        return likeHistory;
    }


}
