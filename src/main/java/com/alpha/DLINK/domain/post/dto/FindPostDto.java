package com.alpha.DLINK.domain.post.dto;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.post.domain.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class FindPostDto {

    private String title;
    private String content;
    private List<FileDto> files;

    public FindPostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        files = post.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
    }

    @Data
    static class FileDto {
        private String url;

        public FileDto(File file) {
            this.url = file.getUrl();
        }
    }

}
