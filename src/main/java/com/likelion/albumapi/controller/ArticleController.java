package com.likelion.albumapi.controller;

import com.likelion.albumapi.dto.ArticleDto;
import com.likelion.albumapi.dto.ArticleModifyDto;
import com.likelion.albumapi.service.ArticleService;
import com.likelion.albumapi.service.S3Service;
import io.swagger.annotations.ApiOperation;
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

@RestController
@RequestMapping(value = "/album/")
@RequiredArgsConstructor
public class ArticleController {
    public final ArticleService articleService;
    public final S3Service s3Service;

    @RequestMapping(value = "articles", method = RequestMethod.GET)
    @ApiOperation(value = "모든 게시물 조회", notes = "전체 게시물을 조회한다.")
    public ResponseEntity<?> findAll() {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        articleDtoList = articleService.findAll();
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "create/article", method = RequestMethod.POST)
    @ApiOperation(value = "게시물 생성", notes = "새로운 게시물을 생성한다")
    public HttpStatus createArticle(MultipartHttpServletRequest mul) throws IOException {
        articleService.createArticle(mul);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "{article_id}", method = RequestMethod.GET)
    @ApiOperation(value = "id에 의한 게시물 조회", notes = "특정 id를 통해 단일 게시물을 찾아온다.")
    public ResponseEntity<?> findArticleById(@PathVariable @Validated Long article_id) {
        ArticleDto articleDto = articleService.findArticleById(article_id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @RequestMapping(value = "delete/article/{article_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "게시물 삭제", notes = "특정 id를 통해 게시물을 삭제한다.")
    public HttpStatus deleteArticleById(@PathVariable  @Validated Long article_id) {
        String fileName = articleService.getFileName(article_id);
        fileName = fileName.replace("https://likelionalbumapi.s3.ap-northeast-2.amazonaws.com/","");
        s3Service.deleteFile(fileName);
        articleService.deleteArticle(article_id);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "modify/article/{article_id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "게시물 수정", notes = "id를 통해 게시물을 수정한다.")
    public HttpStatus modifyArticleById(@PathVariable @Validated Long article_id, @RequestBody ArticleModifyDto articleModifyDto){
        articleService.updateArticle(article_id, articleModifyDto.getTitle(), articleModifyDto.getContent());
        return HttpStatus.OK;
    }
    @RequestMapping(value = "update/article/like/{article_id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "게시물 업데이트", notes = "id를 통해 게시물을 업데이트 한다.")
    public int updateArticleLike(@PathVariable @Validated Long article_id){
        articleService.updateArticleLike(article_id);
        return articleService.getArticleLikeCount(article_id);
    }
}
