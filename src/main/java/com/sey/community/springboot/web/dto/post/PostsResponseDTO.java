package com.sey.community.springboot.web.dto.post;

import com.sey.community.springboot.domain.posts.Posts;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDTO {

    private Long id, authorId;
    private String title, content, author;
    private LocalDateTime modifiedDate;

    @Setter
    private Long viewCount;

    @Setter
    private String fileName;

    public PostsResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.authorId = entity.getAuthorId();
        this.modifiedDate = entity.getModifiedDate();
    }

}
