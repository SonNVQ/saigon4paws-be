package org.saigon4paws.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "adopters")
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Adopter name must not be empty!")
    private String fullName;

    private Date dob;

    private String address;

    private String formOfStay;

    private String job;

    private Integer monthlyIncome;

    private Integer numberOfPeopleLivingTogether;

    private String phoneNumber;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Pet> pets;
}
