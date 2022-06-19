package com.likelion.albumapi.mapper;

import com.likelion.albumapi.domain.Comment;
import com.likelion.albumapi.dto.CommentDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface CommentMapper {
    @Select("select * from comment where article_id = #{article_id} order by date")
    ArrayList<CommentDto> findAllComment(Long article_id);

    @Insert("insert into comment (id, content, article_id, date, comment_like) values(#{id}, #{content}, #{article_id}, #{date}, #{comment_like})")
    void saveComment(Comment article);

    @Delete("delete from comment where id = #{id}")
    void deleteComment(Long id);

    @Update("update comment set content = #{content}, date = #{localDateTime} where id = #{id}")
    void modifyComment(Long id, String content);

    @Update("update comment set comment_like = comment.comment_like + 1 where id = #{id}")
    void updateCommentLike(Long id);

    @Select("select comment_like from comment where id = #{id}")
    int getCommentLikeCount(Long id);
}
