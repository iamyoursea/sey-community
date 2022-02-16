package com.sey.community.springboot.web;

import com.sey.community.springboot.domain.file.File;
import com.sey.community.springboot.service.posts.AmazonS3Service;
import com.sey.community.springboot.service.posts.PostsService;
import com.sey.community.springboot.web.dto.post.PostsResponseDTO;
import com.sey.community.springboot.web.dto.post.PostsSaveRequestDTO;

import com.sey.community.springboot.web.dto.post.PostsUpdateRequestDTO;
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
    private File file = new File();

    @PostMapping("/api/v1/posts")
    public Long save(@RequestPart(value = "key") PostsSaveRequestDTO requestDTO,
                     @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        Long postId =  postsService.save(requestDTO);
        if(!(files ==null)){
            List<String> fileNames = amazonS3Service.uploadImg(amazonS3Service.getBucket(), files);
            for (int i=0; i<fileNames.size(); i++){
                file.setPostId(postId);
                file.setFileUuid(fileNames.get(i));
                file.setFileName((amazonS3Service.getFileNameList()).get(i));
            }
        }
        return postId;
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
