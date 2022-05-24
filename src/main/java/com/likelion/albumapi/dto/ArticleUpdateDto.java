package com.likelion.albumapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleUpdateDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
}
