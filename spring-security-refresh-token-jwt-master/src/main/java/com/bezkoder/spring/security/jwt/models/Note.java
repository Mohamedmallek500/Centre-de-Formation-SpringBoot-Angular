package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "notes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "etudiant_id", "cours_id", "type_note"
                })
        }
)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valeur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_note", nullable = false)
    private TypeNote typeNote;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;
}
