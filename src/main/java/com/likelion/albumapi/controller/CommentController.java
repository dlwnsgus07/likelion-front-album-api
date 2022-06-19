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
    @RequestMapping(value="create/comment", method = RequestMethod.POST)
    public HttpStatus createComment(){
        return HttpStatus.OK;
    }

    @RequestMapping(value="modify/comment/{comment_id}", method = RequestMethod.PATCH)
    public HttpStatus modifyComment(@PathVariable @Validated Long id, @RequestBody @Validated String content){
        commentService.modifyComment(id, content);
        return HttpStatus.OK;
    }

    @RequestMapping(value="delete/comment/{comment_id}", method = RequestMethod.DELETE)
    public HttpStatus deleteComment(@PathVariable @Validated Long id){
        commentService.deleteComment(id);
        return HttpStatus.OK;
    }

    @RequestMapping(value="update/comment/like/{comment_id}", method = RequestMethod.PATCH)
    public int updateCommentLike(@PathVariable @Validated Long id){
        commentService.updateCommentLike(id);
        int countLike = commentService.getCommentLikeCount(id);
        return countLike;
    }
}
