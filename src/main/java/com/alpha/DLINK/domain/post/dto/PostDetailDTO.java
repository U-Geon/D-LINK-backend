package com.alpha.DLINK.domain.post.dto;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailDTO {
    private String title;
    private String content;
    private List<FileDto> files;
    private Long likes;
    private LocalDateTime createdAt;
    private String nickname;
    private Boolean isLike;

    public PostDetailDTO(Post post, Boolean b) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.files = post.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.likes = post.getLikes();
        this.isLike = b;
        this.createdAt = post.getCreatedAt();
        this.nickname = post.getNickname();
    }

    @Data
    private static class FileDto {
        private String url;

        public FileDto(File file) {
            this.url = file.getUrl();
        }
    }
}
