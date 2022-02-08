package com.sey.community.springboot.domain.posts;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//DAO를 사용하지 않고 기본 CRUD 메소드 자동 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p order by p.id DESC")
    List<Posts> findAllDesc();

    // findByXXX: XXX 컬럼을 키워드로 검색
    // Containing: 특정 키워드 포함 여부
    List<Posts> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(String title, String content);


    List<Posts> findAllByOrderByIdDesc(Pageable pageable);

}
