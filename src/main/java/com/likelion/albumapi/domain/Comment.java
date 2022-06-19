package com.likelion.albumapi.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    private Long id;
    private Long article_id;
    private String content;
    private LocalDateTime date;
    private int comment_like;

    public static Comment createComment(Long article_id, String content){
        Comment comment = new Comment();
        comment.setArticle_id(article_id);
        comment.setContent(content);
        comment.setComment_like(0);
        comment.setDate(LocalDateTime.now());
        return comment;
    }
}
