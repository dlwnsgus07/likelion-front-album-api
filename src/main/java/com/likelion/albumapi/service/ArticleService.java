package com.likelion.albumapi.service;

import com.likelion.albumapi.domain.Article;
import com.likelion.albumapi.dto.AllArticleDto;
import com.likelion.albumapi.dto.ArticleDto;
import com.likelion.albumapi.dto.ArticleUpdateDto;
import com.likelion.albumapi.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService implements FileService{
    private final S3Service s3Uploader;
    public final ArticleMapper am;
    //Article 전체를 반환
    public List<AllArticleDto> findAll(){
        return am.findAll();
    }
    public ArticleDto findArticleById(Long id){
        return am.findArticleById(id);
    }

    //Article 를 생성함
    @Override
    public void createArticle(MultipartHttpServletRequest mul) {
        MultipartFile file = mul.getFile("file");
        if (file.getSize() != 0) {
            String sysFileName = null;
            try {
                sysFileName = s3Uploader.upload(file, "static");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Article article = Article.createArticle(
                    mul.getParameter("title"),
                    mul.getParameter("content"),
                    sysFileName
            );
            am.saveArticle(article);
        }
    }

    //Article 수정
    public void updateArticle(Long id, String title, String content){
        ArticleDto articleDto = am.findArticleById(id);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto();
        articleUpdateDto.setId(id);
        if(Objects.equals(title, "")||Objects.equals(title, null)){
            articleUpdateDto.setTitle(articleDto.getTitle());
        }
       else {
            articleUpdateDto.setTitle(title);
        }

       if(Objects.equals(content, "") || Objects.equals(content, null)){
           articleUpdateDto.setContent(articleDto.getContent());
       }
       else{
           articleUpdateDto.setContent(content);
       }
       articleUpdateDto.setLocalDateTime(LocalDateTime.now());
        am.modifyArticle(articleUpdateDto);
    }

    //Article 삭제
    public void deleteArticle(Long id){
        am.deleteArticle(id);
    }

    public void updateArticleLike(Long article_id) {
        am.updateArticleLike(article_id);
    }

    public int getArticleLikeCount(Long article_id) {
        return am.getArticleLikeCount(article_id);
    }

    public String getFileName(Long article_id){
        return am.getFileName(article_id);
    }
}
