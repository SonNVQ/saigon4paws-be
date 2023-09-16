package org.saigon4paws.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCategoryDTO {
    private Integer id;

    @NotEmpty(message = "Article category name must not be empty!")
    private String name;
}
