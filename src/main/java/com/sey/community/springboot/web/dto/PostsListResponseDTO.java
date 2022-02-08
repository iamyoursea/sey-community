package com.sey.community.springboot.web.dto;

import com.sey.community.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDTO {

    private Long id;
    private String title, author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }

}