package com.sey.community.springboot.service.posts;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadImg(String bucket, List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        multipartFile.forEach(file -> {
            if(Objects.requireNonNull(file.getContentType()).contains("image")) {
                String fileName = createFileName(file.getOriginalFilename());
                String fileFormatName = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);

                MultipartFile resizedFile = resizeImage(fileName, fileFormatName, file, 768);

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(resizedFile.getSize());
                objectMetadata.setContentType(file.getContentType());

                try(InputStream inputStream = resizedFile.getInputStream()) {
                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch(IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
                }

                fileNameList.add(fileName);
            }
        });
        return fileNameList;
    }

    /*
    public String uploadImgDirect(String bucket, String fileName, File file) throws IOException {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return "uploadS3 success";
    }
     */
    /*
    public File convert(MultipartFile multipartFile) throws IOException {
        File uploadFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(uploadFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return uploadFile;
    }
    */

    public void deleteFile(String bucket, String deleteFile) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, deleteFile));
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    MultipartFile resizeImage(String fileName, String fileFormatName, MultipartFile originalImage, int targetWidth) {
        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            // newWidth : newHeight = originWidth : originHeight
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
            if(originWidth < targetWidth)
                return originalImage;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }

    public String getBucket(){
        return bucket;
    }
}