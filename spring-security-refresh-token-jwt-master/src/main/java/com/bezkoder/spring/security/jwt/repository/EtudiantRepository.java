package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // 🔥 récupère le dernier étudiant créé (id max)
    Etudiant findTopByOrderByIdDesc();
}
