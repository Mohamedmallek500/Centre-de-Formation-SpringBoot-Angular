package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends
        JpaRepository<Inscription, Long>,
        JpaSpecificationExecutor<Inscription> {

    boolean existsByEtudiantIdAndGroupeId(Long etudiantId, Long groupeId);

    List<Inscription> findByGroupeId(Long groupeId);

    List<Inscription> findByEtudiantId(Long etudiantId);
}
