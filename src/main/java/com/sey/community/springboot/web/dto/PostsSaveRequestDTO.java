package com.sey.community.springboot.web.dto;

import com.sey.community.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDTO {
    private String title;
    private String content;
    private String author;
    private Long authorId;
    private String fileName;
    private String filePath;

    @Builder
    public PostsSaveRequestDTO(String title, String content, String author, Long authorId, String fileName, String filePath) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorId = authorId;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .authorId(authorId)
                .fileName(fileName)
                .filePath(filePath)
                .build();
    }

}
