package org.saigon4paws.Services;

import org.saigon4paws.DTO.ArticleCategoryDTO;
import org.saigon4paws.Models.ArticleCategory;

import java.util.List;

public interface ArticleCategoryService {
    void createArticleCategory(ArticleCategoryDTO ArticleCategoryDTO) throws Exception;

    List<ArticleCategory> getAllArticleCategories();

    ArticleCategory getArticleCategoryById(Integer id);

    ArticleCategoryDTO getArticleCategoryDTOById(Integer id);

    ArticleCategoryDTO updateArticleCategoryDTOById(Integer id, ArticleCategoryDTO ArticleCategoryDTO) throws Exception;

    void deleteArticleCategoryById(Integer id) throws Exception;
}
