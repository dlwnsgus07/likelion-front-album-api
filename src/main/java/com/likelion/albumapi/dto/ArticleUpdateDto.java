package com.likelion.albumapi.dto;

import lombok.Data;

@Data
public class ArticleUpdateDto {
    private Long id;
    private String title;
    private String content;
}
