package org.saigon4paws.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Check;
import org.saigon4paws.Utils.Constants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReliefGroupDTO {
    private Integer id;

    @NotEmpty(message = "Relief group name must not be empty!")
    private String name;

    @NotEmpty(message = "Working area must not be empty!")
    private String workingArea;

    private String description;

    @NotEmpty(message = "Email must not be empty!")
    @Email(message = "Email format is invalid!")
    private String email;

    @NotEmpty(message = "Phone number must not be empty!")
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = "Phone number format is invalid!")
    private String phoneNumber;

    private String fanpageLink;

    @NotEmpty(message = "Bank account number must not be empty!")
    private String bankAccountNumber;

    private String avatarUrl;
}
