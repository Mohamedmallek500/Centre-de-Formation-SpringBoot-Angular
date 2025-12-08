package com.bezkoder.spring.security.jwt.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "etudiant")
public class Etudiant extends Utilisateur {

    @Column(unique = true)
    private String matricule;   // ex: ETU-2025-00001

    @CreationTimestamp          // 🔥 auto à l’insertion
    @Column(updatable = false)  // jamais modifiée après
    private LocalDateTime dateInscription;
}
