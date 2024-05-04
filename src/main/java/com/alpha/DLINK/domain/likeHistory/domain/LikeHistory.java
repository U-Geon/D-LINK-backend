package com.alpha.DLINK.domain.likeHistory.domain;

import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

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