package com.likelion.albumapi.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {
    public static final String IMAGE_REPO = "/Users/dlwnsgus07/Desktop/like-lion/";
    public void createArticle(MultipartHttpServletRequest multipartHttpServletRequest);

}
