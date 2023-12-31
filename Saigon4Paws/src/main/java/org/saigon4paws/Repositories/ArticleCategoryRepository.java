package org.saigon4paws.Repositories;

import org.saigon4paws.Models.Article;
import org.saigon4paws.Models.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {
    ArticleCategory findByName(String name);
}
