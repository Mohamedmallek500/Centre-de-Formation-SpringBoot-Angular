package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.models.Groupe;
import com.bezkoder.spring.security.jwt.models.GroupeCours;
import com.bezkoder.spring.security.jwt.repository.CoursRepository;
import com.bezkoder.spring.security.jwt.repository.GroupeCoursRepository;
import com.bezkoder.spring.security.jwt.repository.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeCoursService {

    private final GroupeCoursRepository groupeCoursRepository;
    private final GroupeRepository groupeRepository;
    private final CoursRepository coursRepository;

    public GroupeCoursService(
            GroupeCoursRepository groupeCoursRepository,
            GroupeRepository groupeRepository,
            CoursRepository coursRepository
    ) {
        this.groupeCoursRepository = groupeCoursRepository;
        this.groupeRepository = groupeRepository;
        this.coursRepository = coursRepository;
    }

    // CREATE
    public GroupeCours affecterCoursAGroupe(
            Long groupeId,
            Long coursId,
            int volumeHoraire,
            int coefficient
    ) {

        if (groupeCoursRepository.existsByGroupeIdAndCoursId(groupeId, coursId)) {
            throw new RuntimeException("Ce cours est déjà affecté à ce groupe");
        }

        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe introuvable"));

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        GroupeCours gc = new GroupeCours();
        gc.setGroupe(groupe);
        gc.setCours(cours);
        gc.setVolumeHoraire(volumeHoraire);
        gc.setCoefficient(coefficient);

        return groupeCoursRepository.save(gc);
    }

    // READ ALL
    public List<GroupeCours> getAll() {
        return groupeCoursRepository.findAll();
    }

    // READ ONE
    public GroupeCours getById(Long id) {
        return groupeCoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));
    }

    // UPDATE
    public GroupeCours update(Long id, int volumeHoraire, int coefficient) {

        GroupeCours gc = groupeCoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));

        gc.setVolumeHoraire(volumeHoraire);
        gc.setCoefficient(coefficient);

        return groupeCoursRepository.save(gc);
    }

    // DELETE
    public void delete(Long id) {
        if (!groupeCoursRepository.existsById(id)) {
            throw new RuntimeException("Affectation introuvable");
        }
        groupeCoursRepository.deleteById(id);
    }

    // LISTE DES COURS D’UN GROUPE
    public List<GroupeCours> getCoursByGroupe(Long groupeId) {
        return groupeCoursRepository.findByGroupeId(groupeId);
    }
}
