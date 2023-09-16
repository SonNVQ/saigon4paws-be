package org.saigon4paws.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.saigon4paws.Models.ProductType;
import org.saigon4paws.Models.ReliefGroup;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private String avatarUrl;

    private Integer productTypeId;

    private Integer reliefGroupId;
}
