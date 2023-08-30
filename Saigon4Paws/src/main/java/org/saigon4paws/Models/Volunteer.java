package org.saigon4paws.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name must not be empty!")
    private String fullName;

    private String address;

    private Date dob;

    private String gender;

    private String phoneNumber;
}
