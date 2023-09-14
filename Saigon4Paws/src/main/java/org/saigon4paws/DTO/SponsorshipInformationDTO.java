package org.saigon4paws.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SponsorshipInformationDTO {
    private Long id;

    @NotEmpty(message = "Sponsor name must not be empty!")
    private String sponsorName;

    private String paymentMethod;

    @NotNull(message = "Amount must not be empty!")
    private Integer amount;

    private String message;

    private Date sponsorDate;

    private String status;

    private int reliefGroupId;
}
