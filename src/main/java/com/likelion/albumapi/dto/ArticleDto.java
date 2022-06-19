package com.likelion.albumapi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String img;
    private int article_like;
    private List<CommentSearchDto> comments;
}
