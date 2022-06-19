package com.likelion.albumapi.controller;

import com.likelion.albumapi.dto.*;
import com.likelion.albumapi.service.ArticleService;
import com.likelion.albumapi.service.CommentService;
import com.likelion.albumapi.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "게시물", tags = "게시글 관련 API")
@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/album/")
@RequiredArgsConstructor
public class ArticleController {
    public final ArticleService articleService;
    public final S3Service s3Service;
    public final CommentService commentService;

    @Operation(summary = "모든 게시물 조회", description = "데이터 베이스에 존재하는 모든 게시물 정보를 json 형태로 전달 받는다.")
    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        List<AllArticleDto> articleDtoList = new ArrayList<>();
        articleDtoList = articleService.findAll();
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }
    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.")
    @RequestMapping(value = "create/article", method = RequestMethod.POST)
    public HttpStatus createArticle(MultipartHttpServletRequest mul) throws IOException {
        articleService.createArticle(mul);
        return HttpStatus.OK;
    }
    @Operation(summary = "id에 의한 게시물 조회", description = "특정 id를 통해 단일 게시물의 내용과 댓글들을 불러온다.")
    @ApiImplicitParam(name="article_id", value = "정보를 불러올 게시글의 Id 정수로 전달(ex: 1, 2, 5), Router를 통해 전달")
    @RequestMapping(value = "{article_id}", method = RequestMethod.GET)
    public ResponseEntity<?> findArticleById(@PathVariable @Validated Long article_id) {
        List<CommentSearchDto> commentDtoList = new ArrayList<>();
        commentDtoList = commentService.findAllComment(article_id);
        ArticleDto articleDto = articleService.findArticleById(article_id);
        articleDto.setComments(commentDtoList);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @Operation(summary = "게시물 삭제", description = "특정 id를 통해 게시물을 삭제한다.")
    @ApiImplicitParam(name="article_id", value = "특정 게시물의 id를 정수로 전달함 (ex: 1, 2, 5, 10)")
    @RequestMapping(value = "delete/article/{article_id}", method = RequestMethod.DELETE)
    public HttpStatus deleteArticleById(@PathVariable  @Validated Long article_id) {
        String fileName = articleService.getFileName(article_id);
        fileName = fileName.replace("https://likelionalbumapi.s3.ap-northeast-2.amazonaws.com/","");
        s3Service.deleteFile(fileName);
        articleService.deleteArticle(article_id);
        return HttpStatus.OK;
    }

    @Operation(summary = "게시물 수정", description = "id를 통해 게시물을 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="article_id", value = "특정 게시물의 id를 정수로 전달함 (ex: 1, 2, 5, 10)"),
            @ApiImplicitParam(name = "articleModifyDto", value = "변경할 제목(title), 내용(content)를 Json 형태로 HTTP Body에 싫어 전송")
    })
    @RequestMapping(value = "modify/article/{article_id}", method = RequestMethod.PATCH)
    public HttpStatus modifyArticleById(@PathVariable @Validated Long article_id, @RequestBody ArticleModifyDto articleModifyDto){
        articleService.updateArticle(article_id, articleModifyDto.getTitle(), articleModifyDto.getContent());
        return HttpStatus.OK;
    }
    @Operation(summary = "게시물 업데이트", description = "id를 통해 게시물의 좋아요 수를 1개 올려주고.")
    @ApiImplicitParam(name="article_id", value = "특정 게시물의 id를 정수로 전달함 (ex: 1, 2, 5, 10)")
    @RequestMapping(value = "update/article/like/{article_id}", method = RequestMethod.PATCH)
    public int updateArticleLike(@PathVariable @Validated Long article_id){
        articleService.updateArticleLike(article_id);
        return articleService.getArticleLikeCount(article_id);
    }
}
