package org.saigon4paws.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.saigon4paws.Utils.Constants;
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

    @NotEmpty(message = "Phone number must not be empty!")
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = "Phone number format is invalid!")
    private String phoneNumber;

    private Long petId;

    private String status;

    private Date createdAt;
}
