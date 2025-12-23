package com.bezkoder.spring.security.jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "formateur")
public class Formateur extends Utilisateur {

    private String specialite;

    @OneToMany(mappedBy = "formateur")
    @JsonIgnore
    private List<Cours> cours;
}
