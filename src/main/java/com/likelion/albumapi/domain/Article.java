package com.likelion.albumapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Article {
    private Long id;
    private String title;
    private String content;
    private String fileName;
    private int article_like;
    private LocalDateTime date;

    public static Article createArticle(String title, String content, String fileName){
        Article article = new Article();
        article.setTitle(title);
        article.setArticle_like(0);
        article.setFileName(fileName);
        article.setContent(content);
        article.setDate(LocalDateTime.now());
        return article;
    }
}
