package com.likelion.albumapi.controller;

import com.likelion.albumapi.dto.CommentDto;
import com.likelion.albumapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/album/")
@RequiredArgsConstructor
public class CommentController {
    public final CommentService commentService;

    @RequestMapping(value="{article_id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllComment(@PathVariable @Validated Long article_id){
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList = commentService.findAllComment(article_id);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    // 추가 작성 필요
    @RequestMapping(value="create/comment/{article_id}", method = RequestMethod.POST)
    public HttpStatus createComment(@PathVariable @Validated Long article_id, @RequestBody @Validated String content){
        // 게시글 아이디, 댓글 내용
        commentService.createComment(article_id, content);
        return HttpStatus.OK;
    }

    @RequestMapping(value="modify/comment/{comment_id}", method = RequestMethod.PATCH)
    public HttpStatus modifyComment(@PathVariable @Validated Long comment_id, @RequestBody @Validated String content){
        commentService.modifyComment(comment_id, content);
        return HttpStatus.OK;
    }

    @RequestMapping(value="delete/comment/{comment_id}", method = RequestMethod.DELETE)
    public HttpStatus deleteComment(@PathVariable @Validated Long comment_id){
        commentService.deleteComment(comment_id);
        return HttpStatus.OK;
    }

    @RequestMapping(value="update/comment/like/{comment_id}", method = RequestMethod.PATCH)
    public int updateCommentLike(@PathVariable @Validated Long comment_id){
        commentService.updateCommentLike(comment_id);
        int countLike = commentService.getCommentLikeCount(comment_id);
        return countLike;
    }
}
