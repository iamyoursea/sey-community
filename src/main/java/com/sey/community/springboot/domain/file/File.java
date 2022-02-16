package com.sey.community.springboot.domain.file;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
//@ToString
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileUuid;

    private Long postId;

    @Builder
    public File(String fileName, String fileUuid, Long postId) {
        this.fileName = fileName;
        this.fileUuid = fileUuid;
        this.postId = postId;
    }
}
