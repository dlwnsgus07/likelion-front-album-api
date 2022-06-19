package com.likelion.albumapi.dto;

import lombok.Data;

@Data
public class CommentSearchDto {
    private Long id;
    private String content;
    private int comment_like;
}
