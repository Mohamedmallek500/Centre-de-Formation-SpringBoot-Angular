package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "groupe_cours",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"groupe_id", "cours_id"})
        }
)
public class GroupeCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private Groupe groupe;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    // personnalisables par groupe
    private int volumeHoraire;
    private int coefficient;
}
