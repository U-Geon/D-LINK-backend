package com.alpha.DLINK.domain.post.dto;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.post.domain.Post;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPostDTO {

    private String title;
    private String content;
    private List<FileDto> files;
    private Long likes;

    public FindPostDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.files = post.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.likes = post.getLikes();
    }

    @Data
    private static class FileDto {
        private String url;

        public FileDto(File file) {
            this.url = file.getUrl();
        }
    }
}
