package org.saigon4paws.Services;

import org.saigon4paws.DTO.ArticleDTO;
import org.saigon4paws.Models.Article;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    Page<Article> findAll(int page, int size);

    Article getArticleById(Long id);

    ArticleDTO getArticleDTOById(Long id);

    ArticleDTO createArticle(ArticleDTO articleDTO);

    ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) throws Exception;

    void deleteArticle(Long id) throws Exception;

    String uploadArticlePhoto(MultipartFile avatar) throws Exception;
}
