package com.sey.community.springboot.web.dto.file;

import com.sey.community.springboot.domain.file.File;
import lombok.Getter;
import lombok.Setter;

@Getter
public class FileResponseDTO {
    private Long id, postId;
    private String fileName, fileUuid;

    public FileResponseDTO(File entity) {
        this.id = entity.getId();
        this.fileName = entity.getFileName();
        this.fileUuid = entity.getFileUuid();
        this.postId = entity.getPostId();
    }
}
