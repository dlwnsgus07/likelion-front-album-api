package com.likelion.albumapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentModifyDto {
    private String content;
    private LocalDateTime date;
}
