package com.likelion.albumapi.dto;

import lombok.Data;

@Data
public class AllArticleDto {
    private Long id;
    private String title;
    private String content;
    private String img;
    private int article_like;
}
