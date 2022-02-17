package com.sey.community.springboot.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select f.fileUuid from File f where f.postId = :id")
    List<String> findUuidByPostId(Long id);

    @Query("select f from File f where f.postId = :id")
    List<String> findAllByPostId(Long id);
}
