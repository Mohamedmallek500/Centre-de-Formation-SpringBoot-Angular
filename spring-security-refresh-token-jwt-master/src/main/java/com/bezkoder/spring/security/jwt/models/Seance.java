package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "seances")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;

    private String salle;

    @Enumerated(EnumType.STRING)
    private TypeSeance typeSeance;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
}
