package com.alpha.DLINK.domain.post.dto;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPostDTO {

    private Long postId;
    private Long likes;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private List<FileDto> files;

    public FindPostDTO(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.nickname = post.getNickname();
        this.createdAt = post.getCreatedAt();
        this.files = post.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
    }

    @Data
    private static class FileDto {
        private String url;

        public FileDto(File file) {
            this.url = file.getUrl();
        }
    }
}
