package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "inscriptions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"etudiant_id", "groupe_id"})
        }
)
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statut;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private Groupe groupe;
}
