package com.likelion.albumapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long article_id;
    private LocalDateTime date;
    private int comment_like;
}