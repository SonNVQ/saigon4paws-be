package org.saigon4paws.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerDTO {
    private Long id;

    @NotEmpty(message = "Name must not be empty!")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @NotEmpty(message = "Phone number must not be empty!")
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = "Phone number format is invalid!")
    private String phoneNumber;

    @NotEmpty(message = "Free time must not be empty!")
    private String freeTime;

    @NotEmpty(message = "Address must not be empty!")
    private String address;

    @NotEmpty(message = "Reason must not be empty!")
    private String reason;

    private String status;

    private Date createdAt;

    private Integer reliefGroupId;
}
