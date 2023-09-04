package org.saigon4paws.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jdk.jshell.Snippet;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetTypeDTO {
    private Integer id;

    @NotBlank(message = "Pet type name must not be empty!")
    private String name;

    private String species;
}
