package org.saigon4paws.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Pet name must not be empty!")
    private String name;

    private String gender;

    private String age;

    private String adoptionStatus;

    private String healthStatus;

    private String vaccinationStatus;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "adopter_id", referencedColumnName = "id")
    private Adopter adopter;

    @ManyToOne
    @JoinColumn(name = "relief_group_id", referencedColumnName = "id")
    private ReliefGroup reliefGroup;
}
