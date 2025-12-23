package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.*;

@Entity
@Table(
        name = "notes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"etudiant_id", "cours_id"})
        }
)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valeur;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;
}
