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
//data, article_id, 없는 DTO따로 생성