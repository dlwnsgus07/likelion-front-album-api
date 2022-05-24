package com.likelion.albumapi.mapper;

import com.likelion.albumapi.domain.Article;
import com.likelion.albumapi.dto.ArticleDto;
import com.likelion.albumapi.dto.ArticleUpdateDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ArticleMapper {
    @Select("select * from article order by date")
    ArrayList<ArticleDto> findAll();

    @Select("select * from article where id = #{id}")
    ArticleDto findArticleById(Long id);

    @Insert("insert into article(title, content, img, date, article_like) values(#{title}, #{content}, #{fileName}, #{date}, #{article_like})")
    void saveArticle(Article article);

    @Delete("delete from article where id = #{id}")
    void deleteArticle(Long id);

    @Update("update article set title = #{title}, content = #{content}, date = #{localDateTime} where id = #{id}")
    void modifyArticle(ArticleUpdateDto articleUpdateDto);

    @Update("update article set article_like + 1 where id = #{article_id}")
    void updateArticleLike(Long article_id);

    @Select("select article_like from article where id = #{article_id}")
    int getArticleLikeCount(Long article_id);
}
