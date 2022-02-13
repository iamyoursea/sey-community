package com.sey.community.springboot.domain.comments;

import com.sey.community.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private Long parentId;

    @Builder
    public Comments(String content, String author, Long parentId){
        this.content = content;
        this.author = author;
        this.parentId = parentId;
    }

    public void update(String content){
        this.content = content;
    }
}