package com.alpha.DLINK.domain.likeHistory;

import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LikeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean isLike;

    public static LikeHistory create() {
        LikeHistory likeHistory = new LikeHistory();
        likeHistory.setIsLike(false);
        return likeHistory;
    }
}