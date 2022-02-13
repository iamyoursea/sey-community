package com.sey.community.springboot.web.dto.comments;

import com.sey.community.springboot.domain.comments.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsListResponseDTO {

    private Long id;
    private String author;
    private String content;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDTO(Comments entity){
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
    }
}