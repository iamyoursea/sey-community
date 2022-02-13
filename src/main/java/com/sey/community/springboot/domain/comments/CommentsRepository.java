package com.sey.community.springboot.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    @Query("SELECT c FROM Comments c WHERE c.parentId = ?1 ORDER BY c.id DESC")
    List<Comments> findByParentIdByOrderByIdDesc(Long parentId);
}