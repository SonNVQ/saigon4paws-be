package org.saigon4paws.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeDTO {
    private Integer id;

    @NotBlank(message = "Pet type name must not be empty!")
    private String name;

}
