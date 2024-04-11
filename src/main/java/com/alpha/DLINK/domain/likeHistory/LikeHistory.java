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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "is_like")
    private Boolean isLike;

    private void setMember(Member member) {
        this.member = member;
        member.getLikeHistories().add(this);
    }

    private void setPost(Post post) {
        this.post = post;
        post.getLikeHistories().add(this);
    }

    public static LikeHistory create(Member member, Post post) {
        LikeHistory likeHistory = new LikeHistory();
        likeHistory.setIsLike(false);
        likeHistory.setMember(member);
        likeHistory.setPost(post);
        return likeHistory;
    }
}