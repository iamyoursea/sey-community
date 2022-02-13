package com.sey.community.springboot.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsUpdateRequestDTO {

    private String content;

    @Builder
    public CommentsUpdateRequestDTO(String content){
        this.content = content;
    }
}