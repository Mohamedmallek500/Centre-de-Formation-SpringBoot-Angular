package com.bezkoder.spring.security.jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groupes")
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionPedagogique sessionPedagogique;

    @OneToMany(mappedBy = "groupe")
    @JsonIgnore
    private List<GroupeCours> groupeCours;
}
