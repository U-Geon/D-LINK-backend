package com.alpha.DLINK.domain.member.entity;

import com.alpha.DLINK.domain.likeHistory.LikeHistory;
import com.alpha.DLINK.domain.post.Post;
import com.alpha.DLINK.domain.recommandHistory.RecommandHistory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, name = "nickname")
    private String nickname;

    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<LikeHistory> likeHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<RecommandHistory> recommandHistories = new ArrayList<>();


    public static Member create(String email) {
        Member member = new Member();
        member.setEmail(email);
        return member;
    }
}