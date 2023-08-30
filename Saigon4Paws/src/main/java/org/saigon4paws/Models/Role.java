package org.saigon4paws.Models;

import jakarta.persistence.*;
import lombok.*;
import org.saigon4paws.Models.Enums.ERole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERole name;

}
