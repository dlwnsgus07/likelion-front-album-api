package com.likelion.albumapi.controller;

import com.likelion.albumapi.dto.ArticleDto;
import com.likelion.albumapi.dto.ArticleModifyDto;
import com.likelion.albumapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/album/")
@RequiredArgsConstructor
public class ArticleController {
    public final ArticleService articleService;

    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        articleDtoList = articleService.findAll();
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "create/article", method = RequestMethod.POST)
    public HttpStatus createArticle(MultipartHttpServletRequest mul) {
        articleService.createArticle(mul);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "{article_id}", method = RequestMethod.GET)
    public ResponseEntity<?> findArticleById(@PathVariable @Validated Long article_id) {
        ArticleDto articleDto = articleService.findArticleById(article_id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @RequestMapping(value = "delete/article/{article_id}", method = RequestMethod.DELETE)
    public HttpStatus deleteArticleById(@PathVariable  @Validated Long article_id) {
        articleService.deleteArticle(article_id);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "modify/article/{article_id}", method = RequestMethod.PATCH)
    public HttpStatus modifyArticleById(@PathVariable @Validated Long article_id, @RequestBody ArticleModifyDto articleModifyDto){
        articleService.updateArticle(article_id, articleModifyDto.getTitle(), articleModifyDto.getContent(), articleModifyDto.getLocalDateTime());
        return HttpStatus.OK;
    }
    @RequestMapping(value = "update/article/like/{article_id}", method = RequestMethod.PATCH)
    public int updateArticleLike(@PathVariable @Validated Long article_id){
        articleService.updateArticleLike(article_id);
        int countLike = articleService.getArticleLikeCount(article_id);
        return countLike;
    }
}
