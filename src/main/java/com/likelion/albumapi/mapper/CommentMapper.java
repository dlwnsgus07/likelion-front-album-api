package com.likelion.albumapi.mapper;

import com.likelion.albumapi.domain.Comment;
import com.likelion.albumapi.dto.CommentDto;
import com.likelion.albumapi.dto.CommentSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Mapper
@Repository
public interface CommentMapper {
    @Select("select * from comment where article_id = #{article_id} order by date")
    ArrayList<CommentSearchDto> findAllComment(Long article_id);

    @Insert("insert into comment (id, content, article_id, date, comment_like) values(#{id}, #{content}, #{article_id}, #{date}, #{comment_like})")
    void saveComment(Comment article);

    @Delete("delete from comment where id = #{id}")
    void deleteComment(Long id);

    @Update("update comment set content = #{content}, date = #{localDateTime} where id = #{id}")
    void modifyComment(Long id, String content, LocalDateTime localDateTime);

    @Update("update comment set comment_like = comment.comment_like + 1 where id = #{comment_id}")
    void updateCommentLike(Long comment_id);

    @Select("select comment_like from comment where id = #{comment_id}")
    int getCommentLikeCount(Long comment_id);
}
