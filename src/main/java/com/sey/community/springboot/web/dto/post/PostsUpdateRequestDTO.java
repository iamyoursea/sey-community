package com.sey.community.springboot.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDTO {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
