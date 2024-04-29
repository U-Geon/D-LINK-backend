package com.alpha.DLINK.domain.post.domain;


import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "likes")
    private Integer likes;

    // 지울 때 같이 삭제 & 생성할 때 같이 영속화하기!
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<LikeHistory> likeHistories = new ArrayList<>();

    public static Post create(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setLikes(0);
        return post;
    }
}