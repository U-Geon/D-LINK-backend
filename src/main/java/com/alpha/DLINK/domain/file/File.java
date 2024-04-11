package com.alpha.DLINK.domain.file;


import com.alpha.DLINK.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private void setPost(Post post) {
        this.post = post;
        post.getFiles().add(this);
    }

    public static File create(String url, Post post) {
        File file = new File();
        file.setUrl(url);
        file.setPost(post);
        return file;
    }
}