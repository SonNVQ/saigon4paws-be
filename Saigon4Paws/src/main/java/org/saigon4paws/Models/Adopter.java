package org.saigon4paws.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    private String status;

    @CreatedDate
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;
}
