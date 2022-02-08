package com.sey.community.springboot.web.dto.notice;

import com.sey.community.springboot.domain.notice.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListResponseDTO {

    private Long id;
    private String title, author;
    private LocalDateTime modifiedDate;

    public NoticeListResponseDTO(Notice entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}