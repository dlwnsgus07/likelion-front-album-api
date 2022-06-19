package com.likelion.albumapi.controller;

import com.likelion.albumapi.dto.CommentDto;
import com.likelion.albumapi.dto.CommentModifyDto;
import com.likelion.albumapi.dto.CommentSearchDto;
import com.likelion.albumapi.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Api(value = "댓글", tags = "댓글 관련 API")
@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
@RestController
@RequestMapping(value="/album/")
@RequiredArgsConstructor
public class CommentController {
    public final CommentService commentService;

    @Operation(summary = "특정 게시글에 대한 전체 댓글 조회", description = "특정 게시글에 대한 전체 댓글을 조회합니다.")
    @ApiImplicitParam(name = "article_id", value = "댓글을 전부 조회할 게시글의 아이디")
    @RequestMapping(value="find/{article_id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllComment(@PathVariable @Validated Long article_id){
        List<CommentSearchDto> commentDtoList = new ArrayList<>();
        commentDtoList = commentService.findAllComment(article_id);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @Operation(summary = "특정 게시글에 댓글 생성", description = "특정 게시글에 대한 댓글을 생성합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "article_id", value = "댓글을 생성할 게시글 아이디"),
            @ApiImplicitParam(name = "commentDto", value = "생성할 댓글의 내용 및 정보")
    })
    @RequestMapping(value="create/comment/{article_id}", method = RequestMethod.POST)
    public HttpStatus createComment(@PathVariable @Validated Long article_id, @RequestBody @Validated CommentDto commentDto){
        // 게시글 아이디, 댓글 내용
        commentService.createComment(article_id, commentDto.getContent());
        return HttpStatus.OK;
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comment_id", value = "수정할 댓글의 아이디"),
            @ApiImplicitParam(name = "commentModifyDto", value = "수정할 댓글의 내용 및 정보")
    })
    @RequestMapping(value="modify/comment/{comment_id}", method = RequestMethod.PATCH)
    public HttpStatus modifyComment(@PathVariable @Validated Long comment_id, @RequestBody @Validated CommentModifyDto commentModifyDto){
        commentModifyDto.setDate(LocalDateTime.now());
        commentService.modifyComment(comment_id, commentModifyDto.getContent(), commentModifyDto.getDate());
        return HttpStatus.OK;
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiImplicitParam(name = "comment_id", value = "삭제할 댓글의 아이디")
    @RequestMapping(value="delete/comment/{comment_id}", method = RequestMethod.DELETE)
    public HttpStatus deleteComment(@PathVariable @Validated Long comment_id){
        commentService.deleteComment(comment_id);
        return HttpStatus.OK;
    }

    @Operation(summary = "댓글 좋아요 증가 후 리턴", description = "댓글의 좋아요 수를 증가시키고 증가된 값을 화면에 표시해줍니다.")
    @ApiImplicitParam(name = "comment_id", value = "좋아요 수를 늘릴 댓글의 아이디")
    @RequestMapping(value="update/comment/like/{comment_id}", method = RequestMethod.PATCH)
    public int updateCommentLike(@PathVariable @Validated Long comment_id){
        commentService.updateCommentLike(comment_id);
        return commentService.getCommentLikeCount(comment_id);
    }
}
