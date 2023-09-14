package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.ArticleDTO;
import org.saigon4paws.Models.Article;
import org.saigon4paws.Models.ArticleCategory;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Repositories.ArticleRepository;
import org.saigon4paws.Services.ArticleCategoryService;
import org.saigon4paws.Services.ArticleService;
import org.saigon4paws.Services.FileService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private FileService fileService;

    @Value("${upload.article-photo.dir}")
    private String articlePhotoDir;

    @Override
    public Page<Article> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAll(pageable);
    }

    @Override
    public Article getArticleById(Long id) {
        if (id == null)
            return null;
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public ArticleDTO getArticleDTOById(Long id) {
        Article article = getArticleById(id);
        if (article == null)
            return null;
        return getArticleDTO(article);
    }

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(articleDTO.getReliefGroupId());
        ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(articleDTO.getArticleCategoryId());
        Article article = Article.builder()
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .postedAt(articleDTO.getPostedAt())
                .author(articleDTO.getAuthor())
                .reliefGroup(reliefGroup)
                .articleCategory(articleCategory)
                .avatarUrl(articleDTO.getAvatarUrl())
                .build();
        Article savedArticle = articleRepository.save(article);
        return getArticleDTO(savedArticle);
    }

    private ArticleDTO getArticleDTO(Article article) {
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .postedAt(article.getPostedAt())
                .author(article.getAuthor())
                .reliefGroupId(article.getReliefGroup().getId())
                .articleCategoryId(article.getArticleCategory().getId())
                .avatarUrl(article.getAvatarUrl())
                .build();
    }

    @Override
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) throws Exception {
        Article article = getArticleById(id);
        if (article == null)
            throw new Exception("Article not found");
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(articleDTO.getReliefGroupId());
        ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(articleDTO.getArticleCategoryId());
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setPostedAt(articleDTO.getPostedAt());
        article.setAuthor(articleDTO.getAuthor());
        article.setReliefGroup(reliefGroup);
        article.setArticleCategory(articleCategory);
        if (articleDTO.getAvatarUrl() != null)
            article.setAvatarUrl(articleDTO.getAvatarUrl());
        Article savedArticle = articleRepository.save(article);
        return getArticleDTO(savedArticle);
    }

    @Override
    public void deleteArticle(Long id) throws Exception {
        Article article = getArticleById(id);
        if (article == null)
            throw new Exception("Article not found");
        articleRepository.delete(article);
    }

    @Override
    public String uploadArticlePhoto(MultipartFile avatar) throws Exception {
        return fileService.saveImageFromMultipartFile(articlePhotoDir, avatar);
    }
}
