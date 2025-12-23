package com.bezkoder.spring.security.jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sessions_pedagogiques")
public class SessionPedagogique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String annee;    // 2025-2026
    private String semestre; // S1, S2

    @OneToMany(mappedBy = "sessionPedagogique")
    @JsonIgnore
    private List<Groupe> groupes;
}
