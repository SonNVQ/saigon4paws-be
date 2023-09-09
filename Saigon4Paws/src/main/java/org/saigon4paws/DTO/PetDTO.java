package org.saigon4paws.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.saigon4paws.Models.Adopter;
import org.saigon4paws.Models.PetType;
import org.saigon4paws.Models.ReliefGroup;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDTO {

    private Long id;

    @NotEmpty(message = "Pet name must not be empty!")
    private String name;

    private String gender;

    private String age;

    private String adoptionStatus;

    private String healthStatus;

    private String vaccinationStatus;

    private Date adoptedDate;

    private String photoUrl;

    @NotNull(message = "Pet type must not be empty!")
    private Integer petTypeId;

    private Long adopterId;

    @NotNull(message = "Relief group must not be empty!")
    private Integer reliefGroupId;
}
