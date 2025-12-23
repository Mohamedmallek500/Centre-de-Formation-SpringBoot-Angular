package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.*;
import com.bezkoder.spring.security.jwt.repository.CoursRepository;
import com.bezkoder.spring.security.jwt.repository.GroupeRepository;
import com.bezkoder.spring.security.jwt.repository.SeanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final GroupeRepository groupeRepository;
    private final CoursRepository coursRepository;

    public SeanceService(
            SeanceRepository seanceRepository,
            GroupeRepository groupeRepository,
            CoursRepository coursRepository
    ) {
        this.seanceRepository = seanceRepository;
        this.groupeRepository = groupeRepository;
        this.coursRepository = coursRepository;
    }

    // =========================
    // CREATE
    // =========================
    public Seance creerSeance(
            Long groupeId,
            Long coursId,
            LocalDateTime debut,
            LocalDateTime fin,
            String salle,
            TypeSeance typeSeance
    ) {

        // 1️⃣ Vérification horaire basique
        if (fin.isBefore(debut) || fin.isEqual(debut)) {
            throw new RuntimeException("Heure de fin invalide");
        }

        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe introuvable"));

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        // 2️⃣ ❌ Conflit SALLE
        if (seanceRepository
                .existsBySalleAndHeureDebutLessThanAndHeureFinGreaterThan(
                        salle, fin, debut
                )) {
            throw new RuntimeException("Salle déjà occupée à cet horaire");
        }

        // 3️⃣ ❌ Conflit FORMATEUR
        if (cours.getFormateur() != null) {
            Long formateurId = cours.getFormateur().getId();

            if (seanceRepository
                    .existsByCours_Formateur_IdAndHeureDebutLessThanAndHeureFinGreaterThan(
                            formateurId, fin, debut
                    )) {
                throw new RuntimeException("Formateur indisponible à cet horaire");
            }
        }

        // 4️⃣ ❌ Conflit GROUPE 🔥
        if (seanceRepository
                .existsByGroupe_IdAndHeureDebutLessThanAndHeureFinGreaterThan(
                        groupeId, fin, debut
                )) {
            throw new RuntimeException("Le groupe a déjà une séance à cet horaire");
        }

        // 5️⃣ Création
        Seance seance = new Seance();
        seance.setGroupe(groupe);
        seance.setCours(cours);
        seance.setHeureDebut(debut);
        seance.setHeureFin(fin);
        seance.setSalle(salle);
        seance.setTypeSeance(typeSeance);

        return seanceRepository.save(seance);
    }

    // =========================
    // READ
    // =========================
    public List<Seance> getAll() {
        return seanceRepository.findAll();
    }

    public Seance getById(Long id) {
        return seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance introuvable"));
    }

    // emploi du temps GROUPE
    public List<Seance> getByGroupe(Long groupeId) {
        return seanceRepository.findByGroupe_Id(groupeId);
    }

    // emploi du temps FORMATEUR
    public List<Seance> getByFormateur(Long formateurId) {
        return seanceRepository.findByCours_Formateur_Id(formateurId);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(Long id) {
        if (!seanceRepository.existsById(id)) {
            throw new RuntimeException("Séance introuvable");
        }
        seanceRepository.deleteById(id);
    }
}
