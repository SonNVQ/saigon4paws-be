package org.saigon4paws.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    private Date postedAt;

    private String author;

    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name = "relief_group_id", referencedColumnName = "id")
    private ReliefGroup reliefGroup;

    @ManyToOne
    @JoinColumn(name = "article_category_id", referencedColumnName = "id")
    private ArticleCategory articleCategory;
}
