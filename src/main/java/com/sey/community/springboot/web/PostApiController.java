package com.sey.community.springboot.web;

import com.sey.community.springboot.service.posts.AmazonS3Service;
import com.sey.community.springboot.service.posts.PostsService;
import com.sey.community.springboot.web.dto.PostsResponseDTO;
import com.sey.community.springboot.web.dto.PostsSaveRequestDTO;

import com.sey.community.springboot.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostsService postsService;
    private final LogApiController logApiController;
    private final AmazonS3Service amazonS3Service;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO requestDTO, List<MultipartFile> multipartFile) {
        amazonS3Service.uploadImg(amazonS3Service.getBucket(), multipartFile);
        return postsService.save(requestDTO);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.update(id, requestDTO);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {
        PostsResponseDTO post = postsService.findById(id);
        post.setViewCount(logApiController.getViewCount("posts", post.getId()));
        return post;
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id, @RequestParam String fileName) {
        amazonS3Service.deleteFile(amazonS3Service.getBucket(), fileName);
        postsService.delete(id);
        return id;
    }

}
