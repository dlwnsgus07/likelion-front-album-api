package com.likelion.albumapi.service;

import com.likelion.albumapi.domain.Comment;
import com.likelion.albumapi.dto.CommentDto;
import com.likelion.albumapi.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentMapper commentMapper;

    // Article에 대한 Comment 전체를 반환
    public List<CommentDto> findAllComment(Long article_id){
        return commentMapper.findAllComment(article_id);
    }

    // Comment를 생성. 추가 작성 필요.
    public void createComment(Long article_id, String content){
        Comment comment = Comment.createComment(article_id, content);
        commentMapper.saveComment(comment);
    }

    // Comment 수정. 추가 작성 필요.
    public void modifyComment(Long id, String content){
        commentMapper.modifyComment(id, content);
    }

    // Comment 삭제
    public void deleteComment(Long id){
        commentMapper.deleteComment(id);
    }

    // Comment 좋아요 수 +
    public void updateCommentLike(Long id){
        commentMapper.updateCommentLike(id);
    }

    // Comment 좋아요 수 반환.
    public int getCommentLikeCount(Long id){
        return commentMapper.getCommentLikeCount(id);
    }

}
