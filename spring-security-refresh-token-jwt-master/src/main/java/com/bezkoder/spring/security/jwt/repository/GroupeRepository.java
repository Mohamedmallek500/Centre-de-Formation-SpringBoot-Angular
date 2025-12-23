package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

    boolean existsByNom(String nom);
}
