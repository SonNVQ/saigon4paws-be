package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.ArticleCategoryDTO;
import org.saigon4paws.DTO.ArticleCategoryDTO;
import org.saigon4paws.Models.ArticleCategory;
import org.saigon4paws.Models.ArticleCategory;
import org.saigon4paws.Repositories.ArticleCategoryRepository;
import org.saigon4paws.Services.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Override
    public void createArticleCategory(ArticleCategoryDTO articleCategoryDTO) throws Exception {
        ArticleCategory existingArticleCategory = articleCategoryRepository.findByName(articleCategoryDTO.getName());
        if (existingArticleCategory != null) {
            throw new Exception("Article category already exists!");
        }
        ArticleCategory articleCategory = ArticleCategory.builder()
                .name(articleCategoryDTO.getName())
                .build();
        articleCategoryRepository.save(articleCategory);
    }

    @Override
    public List<ArticleCategory> getAllArticleCategories() {
        return articleCategoryRepository.findAll();
    }

    @Override
    public ArticleCategory getArticleCategoryById(Integer id) {
        if (id == null)
            return null;
        return articleCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public ArticleCategoryDTO getArticleCategoryDTOById(Integer id) {
        ArticleCategory articleCategory = getArticleCategoryById(id);
        if (articleCategory == null)
            return null;
        return ArticleCategoryDTO.builder()
                .id(articleCategory.getId())
                .name(articleCategory.getName())
                .build();
    }

    @Override
    public ArticleCategoryDTO updateArticleCategoryDTOById(Integer id, ArticleCategoryDTO articleCategoryDTO) throws Exception {
        ArticleCategory articleCategory = getArticleCategoryById(id);
        if (articleCategory == null)
            throw new Exception("Article category not found!");
        articleCategory.setName(articleCategoryDTO.getName());
        articleCategoryRepository.save(articleCategory);
        return ArticleCategoryDTO.builder()
                .id(articleCategory.getId())
                .name(articleCategory.getName())
                .build();
    }

    @Override
    public void deleteArticleCategoryById(Integer id) throws Exception {
        ArticleCategory articleCategory = getArticleCategoryById(id);
        if (articleCategory == null)
            throw new Exception("Article category not found!");
        articleCategoryRepository.delete(articleCategory);
    }
}
