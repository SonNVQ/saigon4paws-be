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
    private Integer id;

    @NotEmpty(message = "Relief group name must not be empty!")
    @Column(unique = true)
    private String name;

    private String workingArea;

    private String description;

    @Email(message = "Email format is invalid!")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String fanpageLink;

    @Column
    private String bankName;

    @Column
    private String bankBin;

    @Column(unique = true)
    private String bankAccountNumber;

    @Column
    private String bankAccountName;

    private String avatarUrl;
}
