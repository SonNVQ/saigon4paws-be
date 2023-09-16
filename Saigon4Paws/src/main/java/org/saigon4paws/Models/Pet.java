package org.saigon4paws.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.saigon4paws.Configurations.JpaAuditingConfig;
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

    private String photoUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date addedDate;

    private Date adoptedDate;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private PetType type;

    @OneToOne
    @JoinColumn(name = "adopter_id", referencedColumnName = "id")
    private Adopter adopter;

    @ManyToOne
    @JoinColumn(name = "relief_group_id", referencedColumnName = "id")
    private ReliefGroup reliefGroup;
}
