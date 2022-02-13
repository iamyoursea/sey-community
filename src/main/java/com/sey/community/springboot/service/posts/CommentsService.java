package com.sey.community.springboot.service.posts;

import com.sey.community.springboot.config.auth.LoginUser;
import com.sey.community.springboot.config.auth.dto.SessionUser;
import com.sey.community.springboot.domain.comments.Comments;
import com.sey.community.springboot.domain.comments.CommentsRepository;
import com.sey.community.springboot.web.dto.comments.CommentsListResponseDTO;
import com.sey.community.springboot.web.dto.comments.CommentsSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Transactional
    public Long save(CommentsSaveRequestDTO requestDto){
        return commentsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CommentsSaveRequestDTO requestDto){
        Comments comments = commentsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 없습니다. id="+id));
        comments.update(requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public List<CommentsListResponseDTO> findByParentIdByOrderByIdDesc(Long id){
        return commentsRepository.findByParentIdByOrderByIdDesc(id).stream()
                .map(CommentsListResponseDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id){
        Comments comments = commentsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다. id="+id));
        commentsRepository.delete(comments);
    }
}

