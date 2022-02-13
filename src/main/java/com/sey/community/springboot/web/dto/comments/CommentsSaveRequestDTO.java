package com.sey.community.springboot.web.dto.comments;

import com.sey.community.springboot.domain.comments.Comments;
import com.sey.community.springboot.domain.posts.PostsRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsSaveRequestDTO {


    private String content;
    private String author;
    private Long parentId;
    private PostsRepository postsRepository;


    @Builder
    public CommentsSaveRequestDTO(String content, String author, Long parentId){
        this.content = content;
        this.author = author;
        this.parentId = parentId;
    }

    public Comments toEntity(){
        return Comments.builder()
                .content(content)
                .author(author)
                .parentId(parentId)
                .build();
    }
}