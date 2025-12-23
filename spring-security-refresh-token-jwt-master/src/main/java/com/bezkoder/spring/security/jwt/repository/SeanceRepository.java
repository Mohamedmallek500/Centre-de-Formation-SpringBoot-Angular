package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    // ❌ Conflit salle
    boolean existsBySalleAndHeureDebutLessThanAndHeureFinGreaterThan(
            String salle,
            LocalDateTime heureFin,
            LocalDateTime heureDebut
    );

    // ❌ Conflit formateur
    boolean existsByCours_Formateur_IdAndHeureDebutLessThanAndHeureFinGreaterThan(
            Long formateurId,
            LocalDateTime heureFin,
            LocalDateTime heureDebut
    );

    // ❌ Conflit groupe  🔥 NOUVEAU
    boolean existsByGroupe_IdAndHeureDebutLessThanAndHeureFinGreaterThan(
            Long groupeId,
            LocalDateTime heureFin,
            LocalDateTime heureDebut
    );

    List<Seance> findByGroupe_Id(Long groupeId);
    List<Seance> findByCours_Formateur_Id(Long formateurId);
}
