package com.sey.community.springboot.web;

import com.sey.community.springboot.service.posts.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AmazonS3Controller {

    private final AmazonS3Service amazonS3Service;
    private final String bucket = amazonS3Service.getBucket();

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping("/api/v1/img")
    public String uploadImg(@RequestPart List<MultipartFile> multipartFile) {
        amazonS3Service.uploadImg(bucket, multipartFile);
        return "success";
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/api/v1/img")
    public String deleteFile(@RequestParam String fileName) {
        //String bucket = amazonS3Service.getBucket();
        amazonS3Service.deleteFile(bucket, fileName);
        return "success";
    }
}