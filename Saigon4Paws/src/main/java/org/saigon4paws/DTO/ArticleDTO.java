package org.saigon4paws.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {
    private Long id;

    @NotEmpty(message = "Article title must not be empty!")
    private String title;

    @NotEmpty(message = "Article content must not be empty!")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date postedAt;

    private String author;

    private String avatarUrl;

    private Integer reliefGroupId;

    private Integer articleCategoryId;
}
