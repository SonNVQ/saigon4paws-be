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
public class AdopterDTO {

    private Long id;

    @NotEmpty(message = "Adopter name must not be empty!")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;

    private String address;

    private String formOfStay;

    private String job;

    private Integer monthlyIncome;

    private Integer numberOfPeopleLivingTogether;

    private String phoneNumber;

    private Long petId;
}
