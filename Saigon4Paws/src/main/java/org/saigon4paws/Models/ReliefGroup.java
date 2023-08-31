package org.saigon4paws.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "relief_groups")
public class ReliefGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Relief group name must not be empty!")
    private String name;

    private String workingArea;

    private String description;

    @Email(message = "Email format is invalid!")
    private String email;

    private String phoneNumber;

    private String fanpageLink;

    private String bankAccountNumber;
}
