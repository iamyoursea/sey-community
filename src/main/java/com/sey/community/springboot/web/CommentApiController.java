package com.sey.community.springboot.web;

import com.sey.community.springboot.config.auth.LoginUser;
import com.sey.community.springboot.config.auth.dto.SessionUser;
import com.sey.community.springboot.service.posts.CommentsService;
import com.sey.community.springboot.web.dto.comments.CommentsSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentsService commentsService;

    @PostMapping("/api/v1/comments")
    public Long save(@RequestBody CommentsSaveRequestDTO requestDto)

    {
        return commentsService.save(requestDto);
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public Long delete(@PathVariable Long id){
        commentsService.delete(id);
        return id;
    }
}