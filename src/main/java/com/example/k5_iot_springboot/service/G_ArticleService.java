package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.G_Article.request.ArticleCreateRequest;
import com.example.k5_iot_springboot.dto.G_Article.request.ArticleUpdateRequest;
import com.example.k5_iot_springboot.dto.G_Article.response.ArticleDetailResponse;
import com.example.k5_iot_springboot.dto.G_Article.response.ArticleListResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface G_ArticleService {
    ResponseDto<ArticleDetailResponse> createArticle(UserPrincipal principal, @Valid ArticleCreateRequest request);

    ResponseDto<List<ArticleListResponse>> getAllArticles();
    ResponseDto<ArticleDetailResponse> getArticleById(Long id);
    ResponseDto<ArticleDetailResponse> updateArticle(UserPrincipal principal, Long id, @Valid ArticleUpdateRequest request);
    ResponseDto<Void> deleteArticle(UserPrincipal principal, Long id);
}
